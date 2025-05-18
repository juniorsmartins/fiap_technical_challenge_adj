package br.com.fiap.tech.challenge_user.adapter.kafka;

import br.com.fiap.tech.challenge_user.adapter.dto.response.ClienteDtoResponse;

public record UsuarioEvent(

        Integer eventId,

        ClienteDtoResponse clienteDtoResponse
) {
}

