package com.sena.gestionaprendices.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Curso(
        Long id,
        @NotBlank @Size(max = 150) String nombre,
        @NotBlank @Size(max = 1000) String descripcion,
        @NotBlank @Size(max = 500) String url) {
}
