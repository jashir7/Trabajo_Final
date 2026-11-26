package com.sena.gestionaprendices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.gestionaprendices.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
