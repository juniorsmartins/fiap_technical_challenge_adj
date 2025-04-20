package br.com.fiap.tech.challenge_user.adapter.kafka.producer;

import br.com.fiap.tech.challenge_user.adapter.dto.response.UsuarioDtoResponse;

public record UsuarioEvent(

        Integer eventId,

        UsuarioDtoResponse usuarioDtoResponse
) {
}

