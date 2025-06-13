package br.com.fiap.tech.challenge_user.infrastructure.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "RestauranteDtoRequest", description = "Transportador de dados de entrada em requisições.")
public record RestauranteDtoRequest(

        @Schema(name = "nome", description = "Nome do restaurante.", example = "Casa das Aves")
        @NotBlank
        String nome
) {
}

