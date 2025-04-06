package br.com.fiap.tech.challenge_user.adapter.dto;

import java.util.UUID;

public record UserDtoResponse(

        UUID userId,

        String nome,

        String email,

        String login,

        String senha
) { }

