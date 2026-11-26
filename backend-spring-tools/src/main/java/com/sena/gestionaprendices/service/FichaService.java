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
        return repository.save(new Ficha(null, f.codigo().trim().toUpperCase(), f.nombre().trim(),
                f.programa().trim(), f.jornada().trim()));
    }
    public Ficha update(Long id, Ficha f) {
        validarCodigo(f.codigo(), id);
        Ficha existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ficha no encontrada"));
        existing.setCodigo(f.codigo().trim().toUpperCase());
        existing.setNombre(f.nombre().trim());
        existing.setPrograma(f.programa().trim());
        existing.setJornada(f.jornada().trim());
        return repository.save(existing);
    }
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Ficha no encontrada");
        repository.deleteById(id);
    }
    private void validarCodigo(String codigo, Long idIgnorado) {
        String codigoNormalizado = codigo.trim();
        boolean duplicado = idIgnorado == null
                ? repository.existsByCodigoIgnoreCase(codigoNormalizado)
                : repository.existsByCodigoIgnoreCaseAndIdNot(codigoNormalizado, idIgnorado);
        if (duplicado) {
            throw new IllegalArgumentException("Ya existe una ficha con ese código");
        }
    }
    public long count() { return repository.count(); }
}
