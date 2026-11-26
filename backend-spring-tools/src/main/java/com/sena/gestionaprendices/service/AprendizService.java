package com.sena.gestionaprendices.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.sena.gestionaprendices.exception.ResourceNotFoundException;
import com.sena.gestionaprendices.model.Aprendiz;
import com.sena.gestionaprendices.repository.AprendizRepository;
import com.sena.gestionaprendices.repository.FichaRepository;

@Service
public class AprendizService {
    private final AprendizRepository repository;
    private final FichaRepository fichaRepository;

    public AprendizService(AprendizRepository repository, FichaRepository fichaRepository) {
        this.repository = repository;
        this.fichaRepository = fichaRepository;
    }

    public List<Aprendiz> findAll() { return repository.findAll(); }

    public Aprendiz create(Aprendiz a) {
        validar(a, null);
        return repository.saveNew(id -> new Aprendiz(id, a.nombres().trim(), a.apellidos().trim(),
                a.tipoDocumento().trim(), a.numeroDocumento().trim(), a.correo().trim().toLowerCase(), a.fichaId()));
    }

    public Aprendiz update(Long id, Aprendiz a) {
        validar(a, id);
        return repository.replace(id, existing -> new Aprendiz(id, a.nombres().trim(), a.apellidos().trim(),
                a.tipoDocumento().trim(), a.numeroDocumento().trim(), a.correo().trim().toLowerCase(), a.fichaId()))
                .orElseThrow(() -> new ResourceNotFoundException("Aprendiz no encontrado"));
    }

    public void delete(Long id) {
        if (!repository.deleteById(id)) throw new ResourceNotFoundException("Aprendiz no encontrado");
    }

    private void validar(Aprendiz a, Long idIgnorado) {
        if (repository.findAll().stream().anyMatch(item -> item.numeroDocumento().equalsIgnoreCase(a.numeroDocumento().trim())
                && !item.id().equals(idIgnorado))) {
            throw new IllegalArgumentException("Ya existe un aprendiz con ese número de documento");
        }
        if (a.fichaId() != null && fichaRepository.findById(a.fichaId()).isEmpty()) {
            throw new IllegalArgumentException("La ficha seleccionada no existe");
        }
    }

    public long count() { return repository.count(); }
}
