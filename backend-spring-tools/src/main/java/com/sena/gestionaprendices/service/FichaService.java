package com.sena.gestionaprendices.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.sena.gestionaprendices.exception.ResourceNotFoundException;
import com.sena.gestionaprendices.model.Ficha;
import com.sena.gestionaprendices.repository.FichaRepository;

@Service
public class FichaService {
    private final FichaRepository repository;
    public FichaService(FichaRepository repository) { this.repository = repository; }
    public List<Ficha> findAll() { return repository.findAll(); }
    public Ficha create(Ficha f) {
        validarCodigo(f.codigo(), null);
        return repository.saveNew(id -> new Ficha(id, f.codigo().trim().toUpperCase(), f.nombre().trim(),
                f.programa().trim(), f.jornada().trim()));
    }
    public Ficha update(Long id, Ficha f) {
        validarCodigo(f.codigo(), id);
        return repository.replace(id, existing -> new Ficha(id, f.codigo().trim().toUpperCase(), f.nombre().trim(),
                f.programa().trim(), f.jornada().trim()))
                .orElseThrow(() -> new ResourceNotFoundException("Ficha no encontrada"));
    }
    public void delete(Long id) {
        if (!repository.deleteById(id)) throw new ResourceNotFoundException("Ficha no encontrada");
    }
    private void validarCodigo(String codigo, Long idIgnorado) {
        if (repository.findAll().stream().anyMatch(f -> f.codigo().equalsIgnoreCase(codigo.trim()) && !f.id().equals(idIgnorado))) {
            throw new IllegalArgumentException("Ya existe una ficha con ese código");
        }
    }
    public long count() { return repository.count(); }
}
