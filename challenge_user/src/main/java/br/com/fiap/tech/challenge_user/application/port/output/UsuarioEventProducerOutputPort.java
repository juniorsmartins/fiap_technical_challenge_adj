package br.com.fiap.tech.challenge_user.application.port.output;

import br.com.fiap.tech.challenge_user.adapter.dto.response.UsuarioDtoResponse;

public interface UsuarioEventProducerOutputPort {

    UsuarioDtoResponse sendUsuario(UsuarioDtoResponse usuarioDtoResponse);
}

