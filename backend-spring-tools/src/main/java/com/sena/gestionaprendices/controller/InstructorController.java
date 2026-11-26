package com.sena.gestionaprendices.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.sena.gestionaprendices.model.Instructor;
import com.sena.gestionaprendices.service.InstructorService;
import jakarta.validation.Valid;

@RestController @RequestMapping("/api/instructores")
public class InstructorController {
    private final InstructorService service;
    public InstructorController(InstructorService service) { this.service = service; }
    @GetMapping public List<Instructor> findAll() { return service.findAll(); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Instructor create(@Valid @RequestBody Instructor i) { return service.create(i); }
    @PutMapping("/{id}")
    public Instructor update(@PathVariable Long id, @Valid @RequestBody Instructor i) { return service.update(id, i); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
