package br.com.fiap.tech.challenge_user.application.port.out;

import br.com.fiap.tech.challenge_user.infrastructure.entity.UsuarioEntity;

import java.util.Optional;

public interface UsuarioFindByLoginOutputPort {

    Optional<UsuarioEntity> findByLogin(String login);
}

