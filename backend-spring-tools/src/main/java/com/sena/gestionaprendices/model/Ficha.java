package com.sena.gestionaprendices.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Ficha(
        Long id,
        @NotBlank @Size(max = 40) String codigo,
        @NotBlank @Size(max = 120) String nombre,
        @NotBlank @Size(max = 150) String programa,
        @NotBlank @Size(max = 40) String jornada) {
}
