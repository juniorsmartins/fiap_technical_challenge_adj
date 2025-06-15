package br.com.fiap.tech.challenge_user.infrastructure.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "RestauranteDtoRequest", description = "Transportador de dados de entrada em requisições.")
public record RestauranteDtoRequest(

        @Schema(name = "nome", description = "Nome do restaurante.", example = "Casa das Aves")
        @NotBlank
        String nome,

        @Schema(name = "endereco", description = "Descrição completa para identificar localização física de imóvel.")
        @NotNull
        EnderecoDtoRequest endereco
) {
}

