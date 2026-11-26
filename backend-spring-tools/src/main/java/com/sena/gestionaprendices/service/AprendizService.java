package com.sena.gestionaprendices.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.sena.gestionaprendices.exception.ResourceNotFoundException;
import com.sena.gestionaprendices.backup.DatabaseJsonStore;
import com.sena.gestionaprendices.model.Aprendiz;
import com.sena.gestionaprendices.repository.AprendizRepository;
import com.sena.gestionaprendices.repository.FichaRepository;

@Service
public class AprendizService {
    private final AprendizRepository repository;
    private final FichaRepository fichaRepository;
    private final DatabaseJsonStore jsonStore;

    public AprendizService(
            AprendizRepository repository, FichaRepository fichaRepository, DatabaseJsonStore jsonStore) {
        this.repository = repository;
        this.fichaRepository = fichaRepository;
        this.jsonStore = jsonStore;
    }

    public List<Aprendiz> findAll() { return repository.findAll(); }

    public Aprendiz create(Aprendiz a) {
        validar(a, null);
        Aprendiz saved = repository.save(new Aprendiz(null, a.nombres().trim(), a.apellidos().trim(),
                a.tipoDocumento().trim(), a.numeroDocumento().trim(), a.correo().trim().toLowerCase(), a.fichaId()));
        jsonStore.exportSnapshot();
        return saved;
    }

    public Aprendiz update(Long id, Aprendiz a) {
        validar(a, id);
        Aprendiz existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aprendiz no encontrado"));
        existing.setNombres(a.nombres().trim());
        existing.setApellidos(a.apellidos().trim());
        existing.setTipoDocumento(a.tipoDocumento().trim());
        existing.setNumeroDocumento(a.numeroDocumento().trim());
        existing.setCorreo(a.correo().trim().toLowerCase());
        existing.setFichaId(a.fichaId());
        Aprendiz saved = repository.save(existing);
        jsonStore.exportSnapshot();
        return saved;
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Aprendiz no encontrado");
        repository.deleteById(id);
        jsonStore.exportSnapshot();
    }

    private void validar(Aprendiz a, Long idIgnorado) {
        String numeroDocumento = a.numeroDocumento().trim();
        boolean duplicado = idIgnorado == null
                ? repository.existsByNumeroDocumentoIgnoreCase(numeroDocumento)
                : repository.existsByNumeroDocumentoIgnoreCaseAndIdNot(numeroDocumento, idIgnorado);
        if (duplicado) {
            throw new IllegalArgumentException("Ya existe un aprendiz con ese número de documento");
        }
        if (a.fichaId() != null && !fichaRepository.existsById(a.fichaId())) {
            throw new IllegalArgumentException("La ficha seleccionada no existe");
        }
    }

    public long count() { return repository.count(); }
}
