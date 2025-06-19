package br.com.fiap.tech.challenge_user.infrastructure.dto.out;

import br.com.fiap.tech.challenge_user.domain.model.enums.TipoCozinhaEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "RestauranteDtoResponse", description = "Transportador de dados de saída em requisições.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RestauranteDtoResponse(

        @Schema(name = "restauranteId", description = "Identificador único do recurso.",
                example = "034eb74c-69ee-4bd4-a064-5c4cc5e9e748")
        UUID restauranteId,

        @Schema(name = "nome", description = "Nome do restaurante.", example = "Casa das Aves")
        String nome,

        @Schema(name = "tipoCozinhaEnum", description = "Perfil culinário do restaurante.", example = "ITALIANA")
        TipoCozinhaEnum tipoCozinhaEnum,

        @Schema(name = "endereco", description = "Descrição completa para identificar localização física de imóvel.")
        EnderecoDtoResponse endereco,

        @Schema(name = "proprietario", description = "Dono do restaurante.")
        ProprietarioDtoResponse proprietario
) {
}

