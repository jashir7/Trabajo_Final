package com.sena.gestionaprendices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.gestionaprendices.model.Competencia;

public interface CompetenciaRepository extends JpaRepository<Competencia, Long> {
    boolean existsByCodigoIgnoreCase(String codigo);

    boolean existsByCodigoIgnoreCaseAndIdNot(String codigo, Long id);
}
