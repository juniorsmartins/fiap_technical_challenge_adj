package br.com.fiap.tech.challenge_user.adapter.controller;

import br.com.fiap.tech.challenge_user.adapter.dto.response.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.application.mapper.InputMapper;
import br.com.fiap.tech.challenge_user.application.mapper.OutputMapper;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioCreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioDeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import br.com.fiap.tech.challenge_user.config.exceptions.http404.UsuarioNotFoundException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Tag(name = "Usuários", description = "Contém recursos de cadastrar, consultar, atualizar e deletar.")
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractUsuarioController<I, O, U, T, E> {

    private final InputMapper<I, U, T> inputMapper;

    private final OutputMapper<T, O, E> outputMapper;

    private final UsuarioCreateInputPort<T> createInputPort;

    private final UsuarioUpdateInputPort<T> updateInputPort;

    private final UsuarioFindByIdOutputPort<E> findByIdOutputPort;

    private final UsuarioDeleteByIdInputPort<T> deleteByIdInputPort;

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(summary = "Cadastrar", description = "Criar um novo recurso.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created - recurso cadastrado com sucesso.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ClienteDtoResponse.class))}
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request - requisição mal formulada.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class))}
                    ),
                    @ApiResponse(responseCode = "404", description = "Not Found - recurso não encontrado.",
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
                    )
            }
    )
    public ResponseEntity<O> create(
            @Parameter(name = "DtoRequest", description = "Para transporte de dados de entrada.", required = true)
            @RequestBody @Valid I dtoRequest) {

        var response = Optional.ofNullable(dtoRequest)
                .map(inputMapper::toDomainIn)
                .map(createInputPort::create)
                .map(outputMapper::toDtoResponse)
                .orElseThrow(() -> {
                    log.error("AbstractUsuarioController - Erro interno do servidor no método create.");
                    return new InternalServerProblemException();
                });

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

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
                    log.error("AbstractUsuarioController - Erro interno do servidor no método update.");
                    return new InternalServerProblemException();
                });

        return ResponseEntity
                .ok()
                .body(response);
    }

    @GetMapping(path = {"/{id}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Consultar", description = "Buscar um recurso por id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK - requisição bem sucedida e com retorno.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ClienteDtoResponse.class))}
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request - requisição mal formulada.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class))}
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error - situação inesperada no servidor.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class))}
                    )
            }
    )
    public ResponseEntity<O> findById(
            @Parameter(name = "id", description = "Identificador único do recurso.",
                    example = "034eb74c-69ee-4bd4-a064-5c4cc5e9e748", required = true)
            @PathVariable(name = "id") final UUID id) {

        var response = findByIdOutputPort.findById(id)
                .map(outputMapper::toResponse)
                .orElseThrow(() -> {
                    log.error("AbstractUsuarioController - Usuário não encontrado por id: {}.", id);
                    return new UsuarioNotFoundException(id);
                });

        return ResponseEntity
                .ok()
                .body(response);
    }

    @DeleteMapping(path = {"/{id}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Delete", description = "Apagar recurso do banco de dados.",
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
                    )
            }
    )
    public ResponseEntity<Void> deleteById(
            @Parameter(name = "id", description = "Identificador único do recurso.",
                    example = "034eb74c-69ee-4bd4-a064-5c4cc5e9e748", required = true)
            @PathVariable(name = "id") final UUID id
    ) {

        Optional.ofNullable(id)
                .ifPresentOrElse(deleteByIdInputPort::deleteById, () -> {
                    log.error("AbstractUsuarioController - Erro interno do servidor no método deleteById.");
                    throw new InternalServerProblemException();
                });

        return ResponseEntity
                .noContent()
                .build();
    }
}

