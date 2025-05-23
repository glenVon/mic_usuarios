package com.altias.mic_autenticacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altias.mic_autenticacion.usuario.User;

public interface UserRepository extends JpaRepository<User, Long> { 
    User findByNombreUsuarioAndPassword(String nombreUsuario, String password);

}
