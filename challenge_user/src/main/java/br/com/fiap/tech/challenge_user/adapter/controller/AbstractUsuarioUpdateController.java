package br.com.fiap.tech.challenge_user.adapter.controller;

import br.com.fiap.tech.challenge_user.adapter.dto.response.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.application.mapper.InputMapper;
import br.com.fiap.tech.challenge_user.application.mapper.OutputMapper;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioUpdateInputPort;
import br.com.fiap.tech.challenge_user.config.exceptions.http500.InternalServerProblemException;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Tag(name = "Usuários", description = "Contém recursos de atualizar.")
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractUsuarioUpdateController<I, O, U, T, E> {

    private final InputMapper<I, U, T> inputMapper;

    private final OutputMapper<T, O, E> outputMapper;

    private final UsuarioUpdateInputPort<T> updateInputPort;

    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(summary = "Atualizar", description = "Modificar dados de um recurso.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK - requisição bem sucedida e com retorno.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ClienteDtoResponse.class))}
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
    public ResponseEntity<O> update(
            @Parameter(name = "UpdateDtoRequest", description = "Para transporte de dados de entrada.", required = true)
            @RequestBody @Valid U updateDtoRequest) {

        var response = Optional.ofNullable(updateDtoRequest)
                .map(inputMapper::toDomainUpdate)
                .map(updateInputPort::update)
                .map(outputMapper::toDtoResponse)
                .orElseThrow(() -> {
                    log.error("AbstractUsuarioUpdateController - Erro interno do servidor no método update.");
                    return new InternalServerProblemException();
                });

        return ResponseEntity
                .ok()
                .body(response);
    }
}

