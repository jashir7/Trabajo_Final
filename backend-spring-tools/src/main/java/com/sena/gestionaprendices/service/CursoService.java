package com.sena.gestionaprendices.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.sena.gestionaprendices.backup.DatabaseJsonStore;
import com.sena.gestionaprendices.exception.ResourceNotFoundException;
import com.sena.gestionaprendices.model.Curso;
import com.sena.gestionaprendices.repository.CursoRepository;

@Service
public class CursoService {
    private final CursoRepository repository;
    private final DatabaseJsonStore jsonStore;
    public CursoService(CursoRepository repository, DatabaseJsonStore jsonStore) {
        this.repository = repository;
        this.jsonStore = jsonStore;
    }
    public List<Curso> findAll() { return repository.findAll(); }
    public Curso create(Curso c) {
        Curso saved = repository.save(new Curso(null, c.nombre().trim(), c.descripcion().trim(), c.url().trim()));
        jsonStore.exportSnapshot();
        return saved;
    }
    public Curso update(Long id, Curso c) {
        Curso existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));
        existing.setNombre(c.nombre().trim());
        existing.setDescripcion(c.descripcion().trim());
        existing.setUrl(c.url().trim());
        Curso saved = repository.save(existing);
        jsonStore.exportSnapshot();
        return saved;
    }
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Curso no encontrado");
        repository.deleteById(id);
        jsonStore.exportSnapshot();
    }
    public long count() { return repository.count(); }
}
