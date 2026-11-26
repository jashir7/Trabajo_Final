package com.sena.gestionaprendices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sena.gestionaprendices.model.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}
