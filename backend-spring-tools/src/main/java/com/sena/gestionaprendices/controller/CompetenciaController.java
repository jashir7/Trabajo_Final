package com.sena.gestionaprendices.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.sena.gestionaprendices.model.Competencia;
import com.sena.gestionaprendices.service.CompetenciaService;
import jakarta.validation.Valid;

@RestController @RequestMapping("/api/competencias")
public class CompetenciaController {
    private final CompetenciaService service;
    public CompetenciaController(CompetenciaService service) { this.service = service; }
    @GetMapping public List<Competencia> findAll() { return service.findAll(); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Competencia create(@Valid @RequestBody Competencia c) { return service.create(c); }
    @PutMapping("/{id}")
    public Competencia update(@PathVariable Long id, @Valid @RequestBody Competencia c) { return service.update(id, c); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
