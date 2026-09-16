package com.iseg.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iseg.model.Rol;
import com.iseg.model.Usuario;
import com.iseg.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existeUsuario(String email) {
        return usuarioRepository.existsById(email);
    }

    public Usuario registrarUsuario(String email, String password) {
        String passwordHasheada = passwordEncoder.encode(password);
        Rol rol = usuarioRepository.count() == 0 ? Rol.ADMIN : Rol.USER;
        Usuario usuario = new Usuario(email, passwordHasheada, rol);
        return usuarioRepository.save(usuario);
    }
}
