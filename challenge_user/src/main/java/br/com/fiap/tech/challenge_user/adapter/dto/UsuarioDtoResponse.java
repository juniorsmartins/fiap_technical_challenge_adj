package br.com.fiap.tech.challenge_user.adapter.dto;

import java.util.UUID;

public record UsuarioDtoResponse(

        UUID usuarioId,

        String nome,

        String email,

        String login,

        String senha
) { }

