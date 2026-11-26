package com.sena.gestionaprendices.backup;

import java.util.List;

import com.sena.gestionaprendices.model.Aprendiz;
import com.sena.gestionaprendices.model.Competencia;
import com.sena.gestionaprendices.model.Curso;
import com.sena.gestionaprendices.model.Evaluacion;
import com.sena.gestionaprendices.model.Ficha;
import com.sena.gestionaprendices.model.Instructor;
import com.sena.gestionaprendices.model.Usuario;

public record DatabaseSnapshot(
        List<Usuario> usuarios,
        List<Aprendiz> aprendices,
        List<Instructor> instructores,
        List<Ficha> fichas,
        List<Competencia> competencias,
        List<Evaluacion> evaluaciones,
        List<Curso> cursos) {
    public DatabaseSnapshot {
        if (usuarios == null || aprendices == null || instructores == null
                || fichas == null || competencias == null
                || evaluaciones == null || cursos == null) {
            throw new IllegalArgumentException("El respaldo JSON debe incluir todas las colecciones");
        }
    }

    public static DatabaseSnapshot empty() {
        return new DatabaseSnapshot(
                List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), List.of());
    }
}
