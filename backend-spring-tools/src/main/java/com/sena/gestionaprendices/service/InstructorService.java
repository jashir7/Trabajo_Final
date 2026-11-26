package com.sena.gestionaprendices.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.sena.gestionaprendices.exception.ResourceNotFoundException;
import com.sena.gestionaprendices.model.Instructor;
import com.sena.gestionaprendices.repository.InstructorRepository;

@Service
public class InstructorService {
    private final InstructorRepository repository;
    public InstructorService(InstructorRepository repository) { this.repository = repository; }
    public List<Instructor> findAll() { return repository.findAll(); }
    public Instructor create(Instructor i) {
        return repository.saveNew(id -> new Instructor(id, i.nombres().trim(), i.apellidos().trim(),
                i.correo().trim().toLowerCase(), i.especialidad().trim()));
    }
    public Instructor update(Long id, Instructor i) {
        return repository.replace(id, existing -> new Instructor(id, i.nombres().trim(), i.apellidos().trim(),
                i.correo().trim().toLowerCase(), i.especialidad().trim()))
                .orElseThrow(() -> new ResourceNotFoundException("Instructor no encontrado"));
    }
    public void delete(Long id) {
        if (!repository.deleteById(id)) throw new ResourceNotFoundException("Instructor no encontrado");
    }
    public long count() { return repository.count(); }
}
