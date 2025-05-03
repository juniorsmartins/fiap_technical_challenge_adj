package br.com.fiap.tech.challenge_user.adapter.dto.request;

import br.com.fiap.tech.challenge_user.application.core.domain.TipoUsuarioEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

import static br.com.fiap.tech.challenge_user.config.ConstantsValidation.*;

@Schema(name = "UsuarioUpdateDtoRequest", description = "Transportador de dados de entrada em requisições.")
public record UsuarioUpdateDtoRequest(

        @Schema(name = "usuarioId", description = "Identificador único do recurso.", example = "034eb74c-69ee-4bd4-a064-5c4cc5e9e748")
        @NotNull
        UUID usuarioId,

        @Schema(name = "nome", description = "Nome do usuário.", example = "Martin Fowler")
        @NotBlank
        @Size(max = MAX_CARACTER_NOME)
        String nome,

        @Schema(name = "email", description = "Endereço de Correio Eletrônico.", example = "fowler@email.com")
        @NotNull
        @Email
        String email,

        @Schema(name = "login", description = "Nickname para acessar a aplicação.", example = "mfowler")
        @NotBlank
        @Size(max = MAX_CARACTER_LOGIN)
        String login,

        @Schema(name = "senha", description = "Segredo para acessar a aplicação.", example = "fowler20")
        @NotBlank
        @Size(max = MAX_CARACTER_SENHA)
        String senha,

        @Schema(name = "tipo", description = "Define o tipo de Usuário.", allowableValues = {"CLIENTE", "PROPRIETARIO"}, example = "CLIENTE")
        @NotNull
        TipoUsuarioEnum tipo,

        @Schema(name = "endereco", description = "Descrição completa para identificar a localização física de um imóvel.")
        EnderecoDtoRequest endereco
) { }

