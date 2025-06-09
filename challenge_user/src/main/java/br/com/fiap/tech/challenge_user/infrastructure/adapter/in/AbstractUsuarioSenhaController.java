package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.application.domain.exception.http500.InternalServerProblemException;
import br.com.fiap.tech.challenge_user.application.port.in.UsuarioSenhaInputPort;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.SenhaDtoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Tag(name = "Usuários", description = "Contém recurso de atualizar senha.")
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractUsuarioSenhaController<E> {

    private final UsuarioSenhaInputPort<E> senhaInputPort;

    @PatchMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Alterar Senha", description = "Modificar senha de um recurso.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content - requisição bem sucedida e sem retorno.",
                            content = {@Content(mediaType = "application/json")}
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request - requisição mal formulada.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class))}
                    ),
                    @ApiResponse(responseCode = "404", description = "Not Found - recurso não encontrado no banco de dados.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class))}
                    ),
                    @ApiResponse(responseCode = "409", description = "Conflict - violação de regras de negócio.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class))}
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error - situação inesperada no servidor.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class))}
                    ),
            }
    )
    public ResponseEntity<Void> updatePassword(
            @Parameter(name = "SenhaDtoRequest", description = "Para transporte de dados de entrada.", required = true)
            @RequestBody @Valid SenhaDtoRequest senhaDtoRequest) {

        Optional.ofNullable(senhaDtoRequest)
                .ifPresentOrElse(dto -> senhaInputPort
                                .updatePassword(dto.usuarioId(), dto.senhaAntiga(), dto.senhaNova()),
                        () -> {
                            log.error("AbstractUsuarioSenhaController - Erro interno do servidor no método updatePassword.");
                            throw new InternalServerProblemException();
                        });

        return ResponseEntity
                .noContent()
                .build();
    }
}

