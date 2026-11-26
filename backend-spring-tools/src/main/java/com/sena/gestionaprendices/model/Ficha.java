package com.sena.gestionaprendices.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "fichas")
public class Ficha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    @Column(nullable = false, length = 40, unique = true)
    private String codigo;

    @NotBlank
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String nombre;

    @NotBlank
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String programa;

    @NotBlank
    @Size(max = 40)
    @Column(nullable = false, length = 40)
    private String jornada;

    protected Ficha() {
    }

    public Ficha(Long id, String codigo, String nombre, String programa, String jornada) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.programa = programa;
        this.jornada = jornada;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPrograma() { return programa; }
    public void setPrograma(String programa) { this.programa = programa; }
    public String getJornada() { return jornada; }
    public void setJornada(String jornada) { this.jornada = jornada; }

    public Long id() { return id; }
    public String codigo() { return codigo; }
    public String nombre() { return nombre; }
    public String programa() { return programa; }
    public String jornada() { return jornada; }
}
