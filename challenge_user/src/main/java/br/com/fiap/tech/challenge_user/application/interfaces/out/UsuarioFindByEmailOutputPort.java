package br.com.fiap.tech.challenge_user.application.interfaces.out;

import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.UsuarioEntity;

import java.util.Optional;

public interface UsuarioFindByEmailOutputPort {

    Optional<UsuarioEntity> findByEmail(String email);
}

