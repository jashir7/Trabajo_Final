package com.sena.gestionaprendices.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.sena.gestionaprendices.model.Curso;
import com.sena.gestionaprendices.service.CursoService;
import jakarta.validation.Valid;

@RestController @RequestMapping("/api/cursos")
public class CursoController {
    private final CursoService service;
    public CursoController(CursoService service) { this.service = service; }
    @GetMapping public List<Curso> findAll() { return service.findAll(); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Curso create(@Valid @RequestBody Curso c) { return service.create(c); }
    @PutMapping("/{id}")
    public Curso update(@PathVariable Long id, @Valid @RequestBody Curso c) { return service.update(id, c); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
