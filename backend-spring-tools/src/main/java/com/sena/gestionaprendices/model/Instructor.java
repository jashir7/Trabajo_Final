package com.sena.gestionaprendices.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Instructor(
        Long id,
        @NotBlank @Size(max = 100) String nombres,
        @NotBlank @Size(max = 100) String apellidos,
        @NotBlank @Email @Size(max = 180) String correo,
        @NotBlank @Size(max = 120) String especialidad) {
}
