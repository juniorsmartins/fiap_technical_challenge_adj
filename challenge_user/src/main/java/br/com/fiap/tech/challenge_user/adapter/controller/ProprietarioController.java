package br.com.fiap.tech.challenge_user.adapter.controller;

import br.com.fiap.tech.challenge_user.adapter.dto.request.ProprietarioDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.response.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.dto.response.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.mapper.AdapterMapper;
import br.com.fiap.tech.challenge_user.application.port.input.ProprietarioCreateInputPort;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Optional;

@Tag(name = "Proprietário", description = "Contém recursos de cadastrar, consultar, atualizar e deletar.")
@Slf4j
@RestController
@RequestMapping(path = {ProprietarioController.URI_PROPRIETARIO})
@RequiredArgsConstructor
public class ProprietarioController {

    protected static final String URI_PROPRIETARIO = "/api/v1/challenge-user/proprietarios";

    private final AdapterMapper adapterMapper;

    private final ProprietarioCreateInputPort proprietarioCreateInputPort;

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
                .orElseThrow();

        log.info("ProprietarioController - requisição concluída no create: {}", response);

        return ResponseEntity
                .created(URI.create(URI_PROPRIETARIO + "/" + response.usuarioId()))
                .body(response);
    }
}

