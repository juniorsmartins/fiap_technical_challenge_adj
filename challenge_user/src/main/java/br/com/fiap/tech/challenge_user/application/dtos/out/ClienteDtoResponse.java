package br.com.fiap.tech.challenge_user.application.dtos.out;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.UUID;

@Schema(name = "ClienteDtoResponse", description = "Transportador de dados de saída em requisições.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ClienteDtoResponse(

        @Schema(name = "usuarioId", description = "Identificador único do recurso.",
                example = "034eb74c-69ee-4bd4-a064-5c4cc5e9e748")
        UUID usuarioId,

        @Schema(name = "nome", description = "Nome do usuário.", example = "Jeff Sutherland")
        String nome,

        @Schema(name = "email", description = "Endereço de Correio Eletrônico.", example = "sutherland@email.com")
        String email,

        @Schema(name = "login", description = "Nickname para acessar a aplicação.", example = "jeffs")
        String login,

        @Schema(name = "senha", description = "Segredo para acessar a aplicação.", example = "jeffs!10")
        String senha,

        @Schema(name = "dataHoraCriacao", description = "Registro de data e hora do cadastro.",
                example = "2023-10-01T12:10:20.000+00:00")
        Date dataHoraCriacao,

        @Schema(name = "dataHoraEdicao", description = "Registro de data e hora da última atualização.",
                example = "2023-10-01T12:10:20.000+00:00")
        Date dataHoraEdicao,

        @Schema(name = "endereco", description = "Descrição completa para identificar localização física de imóvel.")
        EnderecoDtoResponse endereco,

        @Schema(name = "numeroCartaoFidelidade", description = "Registro em programa de fidelidade para benefícios.",
                example = "12345-6789-3245")
        String numeroCartaoFidelidade
) {
}

