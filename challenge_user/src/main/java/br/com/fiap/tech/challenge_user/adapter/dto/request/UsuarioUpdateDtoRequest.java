package br.com.fiap.tech.challenge_user.adapter.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

import static br.com.fiap.tech.challenge_user.config.ConstantsValidation.*;

public record UsuarioUpdateDtoRequest(

        @NotNull
        UUID usuarioId,

        @NotBlank
        @Size(min = 1, max = MAX_CARACTER_NOME)
        String nome,

        @Email
        @Size(min = 1, max = MAX_CARACTER_EMAIL)
        String email,

        @NotBlank
        @Size(min = 1, max = MAX_CARACTER_LOGIN)
        String login,

        @NotBlank
        @Size(min = 1, max = MAX_CARACTER_SENHA)
        String senha
) {
}

