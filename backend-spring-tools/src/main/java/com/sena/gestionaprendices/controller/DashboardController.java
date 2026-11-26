package com.sena.gestionaprendices.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sena.gestionaprendices.service.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final UsuarioService usuarios;
    private final AprendizService aprendices;
    private final InstructorService instructores;
    private final FichaService fichas;
    private final CompetenciaService competencias;
    private final EvaluacionService evaluaciones;
    private final CursoService cursos;

    public DashboardController(UsuarioService usuarios, AprendizService aprendices,
                               InstructorService instructores, FichaService fichas,
                               CompetenciaService competencias, EvaluacionService evaluaciones,
                               CursoService cursos) {
        this.usuarios = usuarios;
        this.aprendices = aprendices;
        this.instructores = instructores;
        this.fichas = fichas;
        this.competencias = competencias;
        this.evaluaciones = evaluaciones;
        this.cursos = cursos;
    }

    @GetMapping("/resumen")
    public Map<String, Long> resumen() {
        Map<String, Long> resumen = new LinkedHashMap<>();
        resumen.put("usuarios", usuarios.count());
        resumen.put("aprendices", aprendices.count());
        resumen.put("instructores", instructores.count());
        resumen.put("fichas", fichas.count());
        resumen.put("competencias", competencias.count());
        resumen.put("evaluaciones", evaluaciones.count());
        resumen.put("cursos", cursos.count());
        return resumen;
    }
}
