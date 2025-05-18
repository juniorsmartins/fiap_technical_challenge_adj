package br.com.fiap.tech.challenge_user.application.port.output;

import br.com.fiap.tech.challenge_user.adapter.dto.response.ClienteDtoResponse;

public interface UsuarioEventProducerOutputPort {

    ClienteDtoResponse sendUsuario(ClienteDtoResponse clienteDtoResponse);
}

