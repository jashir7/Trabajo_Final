package com.sena.gestionaprendices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.gestionaprendices.model.Aprendiz;

public interface AprendizRepository extends JpaRepository<Aprendiz, Long> {
    boolean existsByNumeroDocumentoIgnoreCase(String numeroDocumento);

    boolean existsByNumeroDocumentoIgnoreCaseAndIdNot(String numeroDocumento, Long id);
}
