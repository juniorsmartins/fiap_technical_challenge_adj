package br.com.fiap.tech.challenge_user.adapter.dto;

import br.com.fiap.tech.challenge_user.config.ConstantsValidation;
import io.cucumber.java.ht.Le;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import static br.com.fiap.tech.challenge_user.config.ConstantsValidation.*;

public record UsuarioDtoRequest(

        @NotBlank
        @Length(max = MAX_CARACTER_NOME)
        String nome,

        @NotBlank
        @Length(max = MAX_CARACTER_EMAIL)
        String email,

        @NotBlank
        @Length(max = MAX_CARACTER_LOGIN)
        String login,

        @NotBlank
        @Length(max = MAX_CARACTER_SENHA)
        String senha
) { }

