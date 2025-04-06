package br.com.fiap.tech.challenge_user.application.port.output;

import br.com.fiap.tech.challenge_user.adapter.entity.UsuarioEntity;

public interface UsuarioCreateOutputPort {

    UsuarioEntity save(UsuarioEntity usuarioEntity);
}

