package br.com.fiap.tech.challenge_user.application.port.out;

import br.com.fiap.tech.challenge_user.infrastructure.dto.out.ClienteDtoResponse;

public interface UsuarioEventProducerOutputPort {

    ClienteDtoResponse sendUsuario(ClienteDtoResponse clienteDtoResponse);
}

