package com.sena.gestionaprendices.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sena.gestionaprendices.exception.ResourceNotFoundException;
import com.sena.gestionaprendices.model.Usuario;
import com.sena.gestionaprendices.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> findAll() {
        return repository.findAll();
    }

    public Usuario create(Usuario usuario) {
        validarCorreoUnico(usuario.correo(), null);
        return repository.saveNew(id -> new Usuario(id, usuario.nombres().trim(), usuario.apellidos().trim(), usuario.correo().trim().toLowerCase()));
    }

    public Usuario update(Long id, Usuario usuario) {
        validarCorreoUnico(usuario.correo(), id);
        return repository.replace(id, existing -> new Usuario(id, usuario.nombres().trim(), usuario.apellidos().trim(), usuario.correo().trim().toLowerCase()))
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
    }

    private void validarCorreoUnico(String correo, Long idIgnorado) {
        boolean duplicado = repository.findAll().stream()
                .anyMatch(usuario -> !usuario.id().equals(idIgnorado)
                        && usuario.correo().equalsIgnoreCase(correo.trim()));
        if (duplicado) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo");
        }
    }

    public long count() {
        return repository.count();
    }
}
