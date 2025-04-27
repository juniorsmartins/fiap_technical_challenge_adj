package br.com.fiap.tech.challenge_user.adapter.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static br.com.fiap.tech.challenge_user.config.ConstantsValidation.*;

@Schema(name = "UsuarioDtoRequest", description = "Transportador de dados de entrada em requisições.")
public record UsuarioDtoRequest(

        @Schema(name = "nome", description = "Nome do usuário.", example = "Robert Martin")
        @NotBlank
        @Size(max = MAX_CARACTER_NOME)
        String nome,

        @Schema(name = "email", description = "Endereço de Correio Eletrônico.", example = "martin@email.com")
        @NotNull
        @Email
        String email,

        @Schema(name = "login", description = "Nickname para acessar a aplicação.", example = "rmartin")
        @NotBlank
        @Size(max = MAX_CARACTER_LOGIN)
        String login,

        @Schema(name = "senha", description = "Segredo para acessar a aplicação.", example = "rmartin!123")
        @NotBlank
        @Size(max = MAX_CARACTER_SENHA)
        String senha
) {
}

