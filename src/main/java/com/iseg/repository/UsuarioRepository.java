package com.iseg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iseg.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);
}
