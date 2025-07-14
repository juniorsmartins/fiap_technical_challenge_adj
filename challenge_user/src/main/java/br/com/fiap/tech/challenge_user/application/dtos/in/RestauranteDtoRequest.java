package br.com.fiap.tech.challenge_user.application.dtos.in;

import br.com.fiap.tech.challenge_user.domain.model.enums.TipoCozinhaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.UUID;

@Schema(name = "RestauranteDtoRequest", description = "Transportador de dados de entrada em requisições.")
public record RestauranteDtoRequest(

        @Schema(name = "nome", description = "Nome do restaurante.", example = "Casa das Aves")
        @NotBlank
        String nome,

        @Schema(name = "tipoCozinhaEnum", description = "Perfil culinário do restaurante.", example = "ITALIANA")
        @NotNull
        TipoCozinhaEnum tipoCozinhaEnum,

        @Schema(name = "horaAbertura", description = "Horário de abertura do restaurante (formato HH:mm:ss).",
                example = "08:00:00", format = "time")
        @NotNull
        LocalTime horaAbertura,

        @Schema(name = "horaFechamento", description = "Horário de fechamento do restaurante (formato HH:mm:ss).",
                example = "22:00:00", format = "time")
        @NotNull
        LocalTime horaFechamento,

        @Schema(name = "endereco", description = "Descrição completa para identificar localização física de imóvel.")
        @NotNull
        EnderecoDtoRequest endereco,

        @Schema(name = "proprietario", description = "Chave de identificação do dono do restaurante.")
        @NotNull
        UUID proprietario
) {
}

