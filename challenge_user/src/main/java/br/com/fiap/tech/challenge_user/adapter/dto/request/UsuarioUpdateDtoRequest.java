package br.com.fiap.tech.challenge_user.adapter.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

import static br.com.fiap.tech.challenge_user.config.ConstantsValidation.*;
import static br.com.fiap.tech.challenge_user.config.ConstantsValidation.MAX_CARACTER_SENHA;

public record UsuarioUpdateDtoRequest(

        @NonNull
        UUID usuarioId,

        @NotBlank
        @Length(max = MAX_CARACTER_NOME)
        String nome,

        @Email
        @Length(max = MAX_CARACTER_EMAIL)
        String email,

        @NotBlank
        @Length(max = MAX_CARACTER_LOGIN)
        String login,

        @NotBlank
        @Length(max = MAX_CARACTER_SENHA)
        String senha
) { }

