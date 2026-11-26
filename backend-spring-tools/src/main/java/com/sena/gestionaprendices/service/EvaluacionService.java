package com.sena.gestionaprendices.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.sena.gestionaprendices.exception.ResourceNotFoundException;
import com.sena.gestionaprendices.model.Evaluacion;
import com.sena.gestionaprendices.repository.AprendizRepository;
import com.sena.gestionaprendices.repository.CompetenciaRepository;
import com.sena.gestionaprendices.repository.EvaluacionRepository;

@Service
public class EvaluacionService {
    private final EvaluacionRepository repository;
    private final AprendizRepository aprendizRepository;
    private final CompetenciaRepository competenciaRepository;

    public EvaluacionService(EvaluacionRepository repository, AprendizRepository aprendizRepository,
                             CompetenciaRepository competenciaRepository) {
        this.repository = repository;
        this.aprendizRepository = aprendizRepository;
        this.competenciaRepository = competenciaRepository;
    }

    public List<Evaluacion> findAll() { return repository.findAll(); }

    public Evaluacion create(Evaluacion e) {
        validarReferencias(e);
        return repository.saveNew(id -> new Evaluacion(id, e.aprendizId(), e.competenciaId(), e.calificacion(), e.fecha()));
    }

    public Evaluacion update(Long id, Evaluacion e) {
        validarReferencias(e);
        return repository.replace(id, existing -> new Evaluacion(id, e.aprendizId(), e.competenciaId(), e.calificacion(), e.fecha()))
                .orElseThrow(() -> new ResourceNotFoundException("Evaluación no encontrada"));
    }

    public void delete(Long id) {
        if (!repository.deleteById(id)) throw new ResourceNotFoundException("Evaluación no encontrada");
    }

    private void validarReferencias(Evaluacion e) {
        if (aprendizRepository.findById(e.aprendizId()).isEmpty()) {
            throw new IllegalArgumentException("El aprendiz seleccionado no existe");
        }
        if (competenciaRepository.findById(e.competenciaId()).isEmpty()) {
            throw new IllegalArgumentException("La competencia seleccionada no existe");
        }
    }

    public long count() { return repository.count(); }
}
