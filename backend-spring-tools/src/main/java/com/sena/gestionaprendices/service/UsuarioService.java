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
        return repository.save(new Usuario(null, usuario.nombres().trim(), usuario.apellidos().trim(), usuario.correo().trim().toLowerCase()));
    }

    public Usuario update(Long id, Usuario usuario) {
        validarCorreoUnico(usuario.correo(), id);
        Usuario existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        existing.setNombres(usuario.nombres().trim());
        existing.setApellidos(usuario.apellidos().trim());
        existing.setCorreo(usuario.correo().trim().toLowerCase());
        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Usuario no encontrado");
        repository.deleteById(id);
    }

    private void validarCorreoUnico(String correo, Long idIgnorado) {
        String correoNormalizado = correo.trim();
        boolean duplicado = idIgnorado == null
                ? repository.existsByCorreoIgnoreCase(correoNormalizado)
                : repository.existsByCorreoIgnoreCaseAndIdNot(correoNormalizado, idIgnorado);
        if (duplicado) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo");
        }
    }

    public long count() {
        return repository.count();
    }
}
