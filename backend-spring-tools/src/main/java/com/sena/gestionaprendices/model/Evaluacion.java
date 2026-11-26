package com.sena.gestionaprendices.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "evaluaciones")
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "aprendiz_id", nullable = false)
    private Long aprendizId;

    @NotNull
    @Column(name = "competencia_id", nullable = false)
    private Long competenciaId;

    @NotNull
    @DecimalMin("0")
    @DecimalMax("5")
    @Column(nullable = false)
    private Double calificacion;

    @NotNull
    @Column(nullable = false)
    private LocalDate fecha;

    protected Evaluacion() {
    }

    public Evaluacion(Long id, Long aprendizId, Long competenciaId, Double calificacion, LocalDate fecha) {
        this.id = id;
        this.aprendizId = aprendizId;
        this.competenciaId = competenciaId;
        this.calificacion = calificacion;
        this.fecha = fecha;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAprendizId() { return aprendizId; }
    public void setAprendizId(Long aprendizId) { this.aprendizId = aprendizId; }
    public Long getCompetenciaId() { return competenciaId; }
    public void setCompetenciaId(Long competenciaId) { this.competenciaId = competenciaId; }
    public Double getCalificacion() { return calificacion; }
    public void setCalificacion(Double calificacion) { this.calificacion = calificacion; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Long id() { return id; }
    public Long aprendizId() { return aprendizId; }
    public Long competenciaId() { return competenciaId; }
    public Double calificacion() { return calificacion; }
    public LocalDate fecha() { return fecha; }
}
