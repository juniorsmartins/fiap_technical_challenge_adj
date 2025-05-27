package br.com.fiap.tech.challenge_user.infrastructure.kafka;

import br.com.fiap.tech.challenge_user.infrastructure.dto.out.ClienteDtoResponse;

public record UsuarioEvent(

        Integer eventId,

        ClienteDtoResponse clienteDtoResponse
) {
}

