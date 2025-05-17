package br.com.fiap.tech.challenge_user.adapter.controller;

import br.com.fiap.tech.challenge_user.adapter.dto.request.ClienteDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.request.ClienteUpdateDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.response.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.mapper.AdapterMapper;
import br.com.fiap.tech.challenge_user.application.port.input.ClienteCreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.input.ClienteDeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.port.input.ClienteUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.ClienteFindByIdOutputPort;
import br.com.fiap.tech.challenge_user.config.exceptions.http404.UsuarioNotFoundException;
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
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "Clientes", description = "Contém os recursos de Cadastrar, Consultar, Atualizar e Deletar.")
@Slf4j
@RestController
@RequestMapping(path = {ClienteController.URI_USUARIO})
@RequiredArgsConstructor
public class ClienteController {

    protected static final String URI_USUARIO = "/api/v1/challenge-user/clientes";

    private final ClienteCreateInputPort clienteCreateInputPort;

    private final ClienteUpdateInputPort clienteUpdateInputPort;

    private final ClienteFindByIdOutputPort clienteFindByIdOutputPort;

    private final ClienteDeleteByIdInputPort clienteDeleteByIdInputPort;

    private final AdapterMapper adapterMapper;

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
    public ResponseEntity<ClienteDtoResponse> create(
            @Parameter(name = "ClienteDtoRequest", description = "Objeto para transporte de dados de entrada.",
                    required = true)
            @RequestBody @Valid ClienteDtoRequest clienteDtoRequest) {

        log.info("ClienteController - requisição feita no create: {}", clienteDtoRequest);

        var response = Optional.ofNullable(clienteDtoRequest)
                .map(adapterMapper::toCliente)
                .map(clienteCreateInputPort::create)
                .map(adapterMapper::toClienteDtoResponse)
                .orElseThrow();

        log.info("ClienteController - requisição concluída no create: {}", response);

        return ResponseEntity
                .created(URI.create(URI_USUARIO + "/" + response.usuarioId()))
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
    public ResponseEntity<ClienteDtoResponse> update(
            @Parameter(name = "ClienteUpdateDtoRequest", description = "Objeto para transporte de dados de entrada.",
                    required = true)
            @RequestBody @Valid ClienteUpdateDtoRequest clienteUpdateDtoRequest) {

        log.info("ClienteController - requisição feita no update: {}", clienteUpdateDtoRequest);

        var response = Optional.ofNullable(clienteUpdateDtoRequest)
                .map(adapterMapper::toCliente)
                .map(clienteUpdateInputPort::update)
                .map(adapterMapper::toClienteDtoResponse)
                .orElseThrow();

        log.info("ClienteController - requisição concluída no update: {}", response);

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
    public ResponseEntity<ClienteDtoResponse> findById(
            @Parameter(name = "id", description = "Identificador único do recurso.",
                    example = "034eb74c-69ee-4bd4-a064-5c4cc5e9e748", required = true)
            @PathVariable(name = "id") final UUID id) {

        log.info("ClienteController - requisição feita no findById: {}", id);

        var response = clienteFindByIdOutputPort.findById(id)
                .map(adapterMapper::toClienteDtoResponse)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        log.info("ClienteController - requisição concluída no findById: {}", response);

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
            @PathVariable(name = "id") final UUID id) {

        log.info("ClienteController - requisição feita no deleteById: {}", id);

        Optional.ofNullable(id)
                .ifPresentOrElse(clienteDeleteByIdInputPort::deleteById,
                        () -> {
                            throw new NoSuchElementException();
                        }
                );

        log.info("ClienteController - requisição concluída no deleteById: {}", id);

        return ResponseEntity
                .noContent()
                .build();
    }
}

