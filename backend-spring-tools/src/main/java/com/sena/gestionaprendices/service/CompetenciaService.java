package com.sena.gestionaprendices.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.sena.gestionaprendices.backup.DatabaseJsonStore;
import com.sena.gestionaprendices.exception.ResourceNotFoundException;
import com.sena.gestionaprendices.model.Competencia;
import com.sena.gestionaprendices.repository.CompetenciaRepository;

@Service
public class CompetenciaService {
    private final CompetenciaRepository repository;
    private final DatabaseJsonStore jsonStore;
    public CompetenciaService(CompetenciaRepository repository, DatabaseJsonStore jsonStore) {
        this.repository = repository;
        this.jsonStore = jsonStore;
    }
    public List<Competencia> findAll() { return repository.findAll(); }
    public Competencia create(Competencia c) {
        validarCodigo(c.codigo(), null);
        Competencia saved = repository.save(new Competencia(null, c.codigo().trim().toUpperCase(), c.nombre().trim(), c.descripcion().trim()));
        jsonStore.exportSnapshot();
        return saved;
    }
    public Competencia update(Long id, Competencia c) {
        validarCodigo(c.codigo(), id);
        Competencia existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Competencia no encontrada"));
        existing.setCodigo(c.codigo().trim().toUpperCase());
        existing.setNombre(c.nombre().trim());
        existing.setDescripcion(c.descripcion().trim());
        Competencia saved = repository.save(existing);
        jsonStore.exportSnapshot();
        return saved;
    }
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Competencia no encontrada");
        repository.deleteById(id);
        jsonStore.exportSnapshot();
    }
    private void validarCodigo(String codigo, Long idIgnorado) {
        String codigoNormalizado = codigo.trim();
        boolean duplicado = idIgnorado == null
                ? repository.existsByCodigoIgnoreCase(codigoNormalizado)
                : repository.existsByCodigoIgnoreCaseAndIdNot(codigoNormalizado, idIgnorado);
        if (duplicado) {
            throw new IllegalArgumentException("Ya existe una competencia con ese código");
        }
    }
    public long count() { return repository.count(); }
}
