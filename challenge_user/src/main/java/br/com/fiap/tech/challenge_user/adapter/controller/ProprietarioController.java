package br.com.fiap.tech.challenge_user.adapter.controller;

import br.com.fiap.tech.challenge_user.adapter.dto.request.ProprietarioDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.request.ProprietarioUpdateDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.response.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.dto.response.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.mapper.AdapterMapper;
import br.com.fiap.tech.challenge_user.application.port.input.ProprietarioCreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.input.ProprietarioDeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.port.input.ProprietarioUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.ProprietarioFindByIdOutputPort;
import br.com.fiap.tech.challenge_user.config.exceptions.http404.ProprietarioNotFoundException;
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
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "Proprietário", description = "Contém recursos de cadastrar, consultar, atualizar e deletar.")
@Slf4j
@RestController
@RequestMapping(path = {ProprietarioController.URI_PROPRIETARIO})
@RequiredArgsConstructor
public class ProprietarioController {

    protected static final String URI_PROPRIETARIO = "/api/v1/challenge-user/proprietarios";

    private final AdapterMapper adapterMapper;

    private final ProprietarioCreateInputPort proprietarioCreateInputPort;

    private final ProprietarioUpdateInputPort proprietarioUpdateInputPort;

    private final ProprietarioFindByIdOutputPort proprietarioFindByIdOutputPort;

    private final ProprietarioDeleteByIdInputPort proprietarioDeleteByIdInputPort;

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
    public ResponseEntity<ProprietarioDtoResponse> create(
            @Parameter(name = "ProprietarioDtoRequest", description = "Objeto para transporte de dados de entrada.",
                    required = true) @RequestBody @Valid ProprietarioDtoRequest proprietarioDtoRequest) {

        log.info("ProprietarioController - requisição feita no create: {}", proprietarioDtoRequest);

        var response = Optional.ofNullable(proprietarioDtoRequest)
                .map(adapterMapper::toProprietario)
                .map(proprietarioCreateInputPort::create)
                .map(adapterMapper::toProprietarioDtoResponse)
                .orElseThrow(() -> {
                    log.error("ProprietarioController - Erro interno do servidor no método create.");
                    return new InternalServerProblemException();
                });

        log.info("ProprietarioController - requisição concluída no create: {}", response);

        return ResponseEntity
                .created(URI.create(URI_PROPRIETARIO + "/" + response.usuarioId()))
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
    public ResponseEntity<ProprietarioDtoResponse> update(
            @Parameter(name = "ProprietarioUpdateDtoRequest", description = "Objeto para transporte de dados de entrada.",
                    required = true)
            @RequestBody @Valid ProprietarioUpdateDtoRequest proprietarioUpdateDtoRequest
    ) {

        log.info("ProprietarioController - requisição feita no update: {}", proprietarioUpdateDtoRequest);

        var response = Optional.ofNullable(proprietarioUpdateDtoRequest)
                .map(adapterMapper::toProprietario)
                .map(proprietarioUpdateInputPort::update)
                .map(adapterMapper::toProprietarioDtoResponse)
                .orElseThrow(() -> {
                    log.error("ProprietarioController - Erro interno do servidor no método update.");
                    return new InternalServerProblemException();
                });

        log.info("ProprietarioController - requisição concluída no update: {}", response);

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
    public ResponseEntity<ProprietarioDtoResponse> findById(
            @Parameter(name = "id", description = "Identificador único do recurso.",
                    example = "034eb74c-69ee-4bd4-a064-5c4cc5e9e748", required = true)
            @PathVariable(name = "id") final UUID id
    ) {

        log.info("ProprietarioController - requisição feita no findById: {}", id);

        var response = proprietarioFindByIdOutputPort.findById(id)
                .map(adapterMapper::toProprietarioDtoResponse)
                .orElseThrow(() -> {
                    log.error("ProprietarioController - Proprietário não encontrado por id: {}.", id);
                    return new ProprietarioNotFoundException(id);
                });

        log.info("ProprietarioController - requisição concluída no findById: {}", response);

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

        log.info("ProprietarioController - requisição feita no deleteById: {}", id);

        Optional.ofNullable(id)
                .ifPresentOrElse(proprietarioDeleteByIdInputPort::deleteById, () -> {
                    log.error("ClienteController - Erro interno do servidor no método deleteById.");
                    throw new InternalServerProblemException();
                });

        log.info("ProprietarioController - requisição concluída no deleteById: {}", id);

        return ResponseEntity
                .noContent()
                .build();
    }
}

