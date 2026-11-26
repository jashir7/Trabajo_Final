package com.sena.gestionaprendices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.gestionaprendices.model.Ficha;

public interface FichaRepository extends JpaRepository<Ficha, Long> {
    boolean existsByCodigoIgnoreCase(String codigo);

    boolean existsByCodigoIgnoreCaseAndIdNot(String codigo, Long id);
}
