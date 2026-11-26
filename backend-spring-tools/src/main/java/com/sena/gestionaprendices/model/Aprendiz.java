package com.sena.gestionaprendices.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Aprendiz(
        Long id,
        @NotBlank @Size(max = 100) String nombres,
        @NotBlank @Size(max = 100) String apellidos,
        @NotBlank @Size(max = 30) String tipoDocumento,
        @NotBlank @Size(max = 40) String numeroDocumento,
        @NotBlank @Email @Size(max = 180) String correo,
        Long fichaId) {
}
