package com.sena.gestionaprendices.backup;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sena.gestionaprendices.model.Aprendiz;
import com.sena.gestionaprendices.model.Competencia;
import com.sena.gestionaprendices.model.Curso;
import com.sena.gestionaprendices.model.Evaluacion;
import com.sena.gestionaprendices.model.Ficha;
import com.sena.gestionaprendices.model.Instructor;
import com.sena.gestionaprendices.model.Usuario;
import com.sena.gestionaprendices.repository.AprendizRepository;
import com.sena.gestionaprendices.repository.CompetenciaRepository;
import com.sena.gestionaprendices.repository.CursoRepository;
import com.sena.gestionaprendices.repository.EvaluacionRepository;
import com.sena.gestionaprendices.repository.FichaRepository;
import com.sena.gestionaprendices.repository.InstructorRepository;
import com.sena.gestionaprendices.repository.UsuarioRepository;

import tools.jackson.databind.json.JsonMapper;

@Component
public class DatabaseJsonStore implements ApplicationRunner {
    private static final Logger LOGGER = Logger.getLogger(DatabaseJsonStore.class.getName());
    private static final String CLASSPATH_BACKUP = "data/database.json";

    private final JsonMapper jsonMapper;
    private final Path backupPath;
    private final UsuarioRepository usuarioRepository;
    private final AprendizRepository aprendizRepository;
    private final InstructorRepository instructorRepository;
    private final FichaRepository fichaRepository;
    private final CompetenciaRepository competenciaRepository;
    private final EvaluacionRepository evaluacionRepository;
    private final CursoRepository cursoRepository;

    public DatabaseJsonStore(
            JsonMapper jsonMapper,
            @Value("${app.data-backup.path:data/database.json}") String backupPath,
            UsuarioRepository usuarioRepository,
            AprendizRepository aprendizRepository,
            InstructorRepository instructorRepository,
            FichaRepository fichaRepository,
            CompetenciaRepository competenciaRepository,
            EvaluacionRepository evaluacionRepository,
            CursoRepository cursoRepository) {
        this.jsonMapper = jsonMapper;
        this.backupPath = Path.of(backupPath).toAbsolutePath().normalize();
        this.usuarioRepository = usuarioRepository;
        this.aprendizRepository = aprendizRepository;
        this.instructorRepository = instructorRepository;
        this.fichaRepository = fichaRepository;
        this.competenciaRepository = competenciaRepository;
        this.evaluacionRepository = evaluacionRepository;
        this.cursoRepository = cursoRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws IOException {
        if (isDatabaseEmpty()) {
            restore(readSnapshot());
            LOGGER.info("La base de datos vacía se inicializó desde el respaldo JSON.");
        }
        writeSnapshot(currentSnapshot());
    }

    @Transactional(readOnly = true)
    public synchronized void exportSnapshot() {
        writeSnapshot(currentSnapshot());
    }

    private boolean isDatabaseEmpty() {
        return usuarioRepository.count() == 0
                && aprendizRepository.count() == 0
                && instructorRepository.count() == 0
                && fichaRepository.count() == 0
                && competenciaRepository.count() == 0
                && evaluacionRepository.count() == 0
                && cursoRepository.count() == 0;
    }

    private DatabaseSnapshot readSnapshot() throws IOException {
        if (Files.isRegularFile(backupPath)) {
            return jsonMapper.readValue(backupPath.toFile(), DatabaseSnapshot.class);
        }

        ClassPathResource resource = new ClassPathResource(CLASSPATH_BACKUP);
        if (resource.exists()) {
            try (InputStream input = resource.getInputStream()) {
                return jsonMapper.readValue(input, DatabaseSnapshot.class);
            }
        }
        return DatabaseSnapshot.empty();
    }

    private void restore(DatabaseSnapshot snapshot) {
        Map<Long, Long> fichaIds = new HashMap<>();
        for (Ficha ficha : snapshot.fichas()) {
            Long originalId = ficha.id();
            ficha.setId(null);
            Ficha saved = fichaRepository.save(ficha);
            fichaIds.put(originalId, saved.id());
        }

        Map<Long, Long> competenciaIds = new HashMap<>();
        for (Competencia competencia : snapshot.competencias()) {
            Long originalId = competencia.id();
            competencia.setId(null);
            Competencia saved = competenciaRepository.save(competencia);
            competenciaIds.put(originalId, saved.id());
        }

        Map<Long, Long> aprendizIds = new HashMap<>();
        for (Aprendiz aprendiz : snapshot.aprendices()) {
            Long originalId = aprendiz.id();
            aprendiz.setId(null);
            if (aprendiz.fichaId() != null) {
                aprendiz.setFichaId(remapId(fichaIds, aprendiz.fichaId(), "ficha"));
            }
            Aprendiz saved = aprendizRepository.save(aprendiz);
            aprendizIds.put(originalId, saved.id());
        }

        for (Instructor instructor : snapshot.instructores()) {
            instructor.setId(null);
            instructorRepository.save(instructor);
        }

        for (Usuario usuario : snapshot.usuarios()) {
            usuario.setId(null);
            usuarioRepository.save(usuario);
        }

        for (Curso curso : snapshot.cursos()) {
            curso.setId(null);
            cursoRepository.save(curso);
        }

        for (Evaluacion evaluacion : snapshot.evaluaciones()) {
            evaluacion.setId(null);
            evaluacion.setAprendizId(remapId(aprendizIds, evaluacion.aprendizId(), "aprendiz"));
            evaluacion.setCompetenciaId(remapId(competenciaIds, evaluacion.competenciaId(), "competencia"));
            evaluacionRepository.save(evaluacion);
        }
    }

    private Long remapId(Map<Long, Long> idMap, Long originalId, String entityName) {
        Long mappedId = idMap.get(originalId);
        if (mappedId == null) {
            throw new IllegalArgumentException(
                    "El respaldo contiene una referencia a " + entityName + " inexistente");
        }
        return mappedId;
    }

    private DatabaseSnapshot currentSnapshot() {
        return new DatabaseSnapshot(
                usuarioRepository.findAll(),
                aprendizRepository.findAll(),
                instructorRepository.findAll(),
                fichaRepository.findAll(),
                competenciaRepository.findAll(),
                evaluacionRepository.findAll(),
                cursoRepository.findAll());
    }

    private void writeSnapshot(DatabaseSnapshot snapshot) {
        Path temporaryPath = null;
        try {
            Path parent = backupPath.getParent();
            Files.createDirectories(parent);
            temporaryPath = Files.createTempFile(parent, backupPath.getFileName().toString(), ".tmp");
            try (OutputStream output = Files.newOutputStream(temporaryPath)) {
                jsonMapper.writerWithDefaultPrettyPrinter().writeValue(output, snapshot);
            }
            try {
                Files.move(temporaryPath, backupPath,
                        StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException exception) {
                Files.move(temporaryPath, backupPath, StandardCopyOption.REPLACE_EXISTING);
            }
            LOGGER.info("Respaldo JSON actualizado en " + backupPath);
        } catch (IOException exception) {
            throw new UncheckedIOException("No se pudo actualizar el respaldo JSON en " + backupPath, exception);
        } finally {
            if (temporaryPath != null) {
                try {
                    Files.deleteIfExists(temporaryPath);
                } catch (IOException exception) {
                    LOGGER.warning("No se pudo eliminar el archivo temporal de respaldo: " + exception.getMessage());
                }
            }
        }
    }
}
