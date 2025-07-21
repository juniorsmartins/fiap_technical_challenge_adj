package br.com.fiap.tech.challenge_user.application.dtos.filters;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UsuarioFiltroDto", description = "Transportador de dados de entrada para operação de pesquisar.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UsuarioFiltroDto(

        @Schema(name = "propriedadeId", description = "Identificador único do recurso.",
                example = "034eb74c-69ee-4bd4-a064-5c4cc5e9e748")
        String usuarioId,

        @Schema(name = "Nome", description = "Nome do cliente.", example = "Alistair Cockburn")
        String nome,

        @Schema(name = "email", description = "Endereço de Correio Eletrônico.", example = "martin@email.com")
        String email,

        @Schema(name = "numeroCartaoFidelidade", description = "Registro em programa de fidelidade para benefícios.",
                example = "12345-6789-3245")
        String numeroCartaoFidelidade,

        @Schema(name = "descricao", description = "Espaço livre para anotações.",
                example = "Atua como contador da empresa.")
        String descricao
) {
}

