package br.com.fiap.tech.challenge_user.adapter.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EnderecoDtoRequest", description = "Transportador de dados de entrada em requisições.")
public record EnderecoDtoRequest(

        @Schema(name = "cep", description = "Código de Endereçamento Postal.", example = "01001-000")
        String cep,

        @Schema(name = "logradouro", description = "Nome dado para espaço público ou via oficialmente reconhecida.", example = "Avenida Central")
        String logradouro,

        @Schema(name = "numero", description = "Identificador da localização exata de um imóvel num logradouro.", example = "1500")
        String numero
) { }

