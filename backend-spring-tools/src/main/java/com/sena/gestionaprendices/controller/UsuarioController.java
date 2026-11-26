package com.sena.gestionaprendices.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.sena.gestionaprendices.model.Usuario;
import com.sena.gestionaprendices.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@Validated
public class UsuarioController {
    private final UsuarioService service;
    public UsuarioController(UsuarioService service) { this.service = service; }

    @GetMapping
    public List<Usuario> findAll() { return service.findAll(); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario create(@Valid @RequestBody Usuario usuario) { return service.create(usuario); }

    @PutMapping("/{id}")
    public Usuario update(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        return service.update(id, usuario);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
