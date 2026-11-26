package com.sena.gestionaprendices.model;

import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record Evaluacion(
        Long id,
        @NotNull Long aprendizId,
        @NotNull Long competenciaId,
        @NotNull @DecimalMin("0") @DecimalMax("5") Double calificacion,
        @NotNull LocalDate fecha) {
}
