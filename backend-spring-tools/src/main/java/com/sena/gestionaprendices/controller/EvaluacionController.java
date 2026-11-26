package com.sena.gestionaprendices.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.sena.gestionaprendices.model.Evaluacion;
import com.sena.gestionaprendices.service.EvaluacionService;
import jakarta.validation.Valid;

@RestController @RequestMapping("/api/evaluaciones")
public class EvaluacionController {
    private final EvaluacionService service;
    public EvaluacionController(EvaluacionService service) { this.service = service; }
    @GetMapping public List<Evaluacion> findAll() { return service.findAll(); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Evaluacion create(@Valid @RequestBody Evaluacion e) { return service.create(e); }
    @PutMapping("/{id}")
    public Evaluacion update(@PathVariable Long id, @Valid @RequestBody Evaluacion e) { return service.update(id, e); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
