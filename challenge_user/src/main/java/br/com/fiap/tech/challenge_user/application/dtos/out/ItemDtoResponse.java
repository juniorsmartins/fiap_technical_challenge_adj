package br.com.fiap.tech.challenge_user.application.dtos.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(name = "ItemDtoResponse", description = "Transportador de dados de saída em requisições.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ItemDtoResponse(

        @Schema(name = "itemId", description = "Identificador único do recurso.",
                example = "034eb74c-69ee-4bd4-a064-5c4cc5e9e748")
        UUID itemId,

        @Schema(name = "nome", description = "Nome do item do cardápio.", example = "Coca-Cola")
        @NotBlank
        String nome,

        @Schema(name = "descrição", description = "Descrição do item do cardápio.", example = "Refrigerante")
        @NotBlank
        String descricao,

        @Schema(name = "preço", description = "Preço de venda do item do cardápio.", example = "20.50",
                format = "decimal")
        @NotNull
        BigDecimal preco,

        @Schema(name = "entrega", description = "Item entregável por delivery (true or false).", example = "true",
                format = "boolean")
        @NotNull
        boolean entrega,

        @Schema(name = "foto", description = "Foto do item do cardápio.", example = "http://link-foto.com.br")
        @NotBlank
        String foto
) {
}

