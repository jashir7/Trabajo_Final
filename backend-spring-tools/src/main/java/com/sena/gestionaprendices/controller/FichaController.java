package com.sena.gestionaprendices.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.sena.gestionaprendices.model.Ficha;
import com.sena.gestionaprendices.service.FichaService;
import jakarta.validation.Valid;

@RestController @RequestMapping("/api/fichas")
public class FichaController {
    private final FichaService service;
    public FichaController(FichaService service) { this.service = service; }
    @GetMapping public List<Ficha> findAll() { return service.findAll(); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Ficha create(@Valid @RequestBody Ficha f) { return service.create(f); }
    @PutMapping("/{id}")
    public Ficha update(@PathVariable Long id, @Valid @RequestBody Ficha f) { return service.update(id, f); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
