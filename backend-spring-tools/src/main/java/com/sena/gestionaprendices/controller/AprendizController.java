package com.sena.gestionaprendices.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.sena.gestionaprendices.model.Aprendiz;
import com.sena.gestionaprendices.service.AprendizService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/aprendices")
public class AprendizController {
    private final AprendizService service;
    public AprendizController(AprendizService service) { this.service = service; }
    @GetMapping public List<Aprendiz> findAll() { return service.findAll(); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Aprendiz create(@Valid @RequestBody Aprendiz a) { return service.create(a); }
    @PutMapping("/{id}")
    public Aprendiz update(@PathVariable Long id, @Valid @RequestBody Aprendiz a) { return service.update(id, a); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
