package com.sena.gestionaprendices.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.sena.gestionaprendices.backup.DatabaseJsonStore;
import com.sena.gestionaprendices.exception.ResourceNotFoundException;
import com.sena.gestionaprendices.model.Instructor;
import com.sena.gestionaprendices.repository.InstructorRepository;

@Service
public class InstructorService {
    private final InstructorRepository repository;
    private final DatabaseJsonStore jsonStore;
    public InstructorService(InstructorRepository repository, DatabaseJsonStore jsonStore) {
        this.repository = repository;
        this.jsonStore = jsonStore;
    }
    public List<Instructor> findAll() { return repository.findAll(); }
    public Instructor create(Instructor i) {
        Instructor saved = repository.save(new Instructor(null, i.nombres().trim(), i.apellidos().trim(),
                i.correo().trim().toLowerCase(), i.especialidad().trim()));
        jsonStore.exportSnapshot();
        return saved;
    }
    public Instructor update(Long id, Instructor i) {
        Instructor existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor no encontrado"));
        existing.setNombres(i.nombres().trim());
        existing.setApellidos(i.apellidos().trim());
        existing.setCorreo(i.correo().trim().toLowerCase());
        existing.setEspecialidad(i.especialidad().trim());
        Instructor saved = repository.save(existing);
        jsonStore.exportSnapshot();
        return saved;
    }
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Instructor no encontrado");
        repository.deleteById(id);
        jsonStore.exportSnapshot();
    }
    public long count() { return repository.count(); }
}
