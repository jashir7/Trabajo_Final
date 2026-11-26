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
        return repository.save(new Instructor(null, i.nombres().trim(), i.apellidos().trim(),
                i.correo().trim().toLowerCase(), i.especialidad().trim()));
    }
    public Instructor update(Long id, Instructor i) {
        Instructor existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor no encontrado"));
        existing.setNombres(i.nombres().trim());
        existing.setApellidos(i.apellidos().trim());
        existing.setCorreo(i.correo().trim().toLowerCase());
        existing.setEspecialidad(i.especialidad().trim());
        return repository.save(existing);
    }
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Instructor no encontrado");
        repository.deleteById(id);
    }
    public long count() { return repository.count(); }
}
