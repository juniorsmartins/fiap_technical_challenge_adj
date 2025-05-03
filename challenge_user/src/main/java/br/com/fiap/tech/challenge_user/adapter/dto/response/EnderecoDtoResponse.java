package br.com.fiap.tech.challenge_user.adapter.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "EnderecoDtoResponse", description = "Transportador de dados de saída.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record EnderecoDtoResponse(

        @Schema(name = "enderecoId", description = "Identificador único do recurso.", example = "034eb74c-69ee-4bd4-a064-5c4cc5e9e748")
        UUID enderecoId,

        @Schema(name = "cep", description = "Código de Endereçamento Postal.", example = "01001-000")
        String cep,

        @Schema(name = "logradouro", description = "Nome dado para espaço público ou via oficialmente reconhecida.", example = "Avenida Central")
        String logradouro,

        @Schema(name = "numero", description = "Identificador da localização exata de um imóvel num logradouro.", example = "1500")
        String numero
) {
}

