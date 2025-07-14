package br.com.fiap.tech.challenge_user.application.dtos.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

import static br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsValidation.MAX_CARACTER_SENHA;

@Schema(name = "SenhaDtoRequest", description = "Transporte de dados de entrada em requisições para troca de senha.")
public record SenhaDtoRequest(

        @Schema(name = "usuarioId", description = "Identificador único do recurso.",
                example = "034eb74c-69ee-4bd4-a064-5c4cc5e9e748")
        @NotNull
        UUID usuarioId,

        @Schema(name = "senha antiga", description = "Segredo deprecado para acessar a aplicação.", example = "rmartin!123")
        @NotBlank
        @Size(max = MAX_CARACTER_SENHA)
        String senhaAntiga,

        @Schema(name = "senha nova", description = "Segredo atualizado para acessar a aplicação.", example = "bob!456")
        @NotBlank
        @Size(max = MAX_CARACTER_SENHA)
        String senhaNova
) {
}

