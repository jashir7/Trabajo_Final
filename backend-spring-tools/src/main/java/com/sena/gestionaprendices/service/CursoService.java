package com.sena.gestionaprendices.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.sena.gestionaprendices.exception.ResourceNotFoundException;
import com.sena.gestionaprendices.model.Curso;
import com.sena.gestionaprendices.repository.CursoRepository;

@Service
public class CursoService {
    private final CursoRepository repository;
    public CursoService(CursoRepository repository) { this.repository = repository; }
    public List<Curso> findAll() { return repository.findAll(); }
    public Curso create(Curso c) {
        return repository.save(new Curso(null, c.nombre().trim(), c.descripcion().trim(), c.url().trim()));
    }
    public Curso update(Long id, Curso c) {
        Curso existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));
        existing.setNombre(c.nombre().trim());
        existing.setDescripcion(c.descripcion().trim());
        existing.setUrl(c.url().trim());
        return repository.save(existing);
    }
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Curso no encontrado");
        repository.deleteById(id);
    }
    public long count() { return repository.count(); }
}
