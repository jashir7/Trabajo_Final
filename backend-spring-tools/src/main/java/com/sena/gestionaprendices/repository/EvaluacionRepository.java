package com.sena.gestionaprendices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.gestionaprendices.model.Evaluacion;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
}
