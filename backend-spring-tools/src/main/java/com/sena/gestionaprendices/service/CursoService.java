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
        return repository.saveNew(id -> new Curso(id, c.nombre().trim(), c.descripcion().trim(), c.url().trim()));
    }
    public Curso update(Long id, Curso c) {
        return repository.replace(id, existing -> new Curso(id, c.nombre().trim(), c.descripcion().trim(), c.url().trim()))
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));
    }
    public void delete(Long id) {
        if (!repository.deleteById(id)) throw new ResourceNotFoundException("Curso no encontrado");
    }
    public long count() { return repository.count(); }
}
