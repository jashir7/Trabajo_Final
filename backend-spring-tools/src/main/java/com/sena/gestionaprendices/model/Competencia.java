package com.sena.gestionaprendices.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Competencia(
        Long id,
        @NotBlank @Size(max = 40) String codigo,
        @NotBlank @Size(max = 150) String nombre,
        @NotBlank @Size(max = 1000) String descripcion) {
}
