package br.com.fiap.tech.challenge_user.application.port.output;

import br.com.fiap.tech.challenge_user.adapter.entity.UsuarioEntity;

import java.util.UUID;

public interface UsuarioFindByIdOutputPort {

    UsuarioEntity findById(UUID id);
}
