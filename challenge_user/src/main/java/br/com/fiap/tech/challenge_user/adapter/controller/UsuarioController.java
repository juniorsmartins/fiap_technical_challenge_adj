package br.com.fiap.tech.challenge_user.adapter.controller;

import br.com.fiap.tech.challenge_user.adapter.dto.request.UsuarioDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.request.UsuarioUpdateDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.response.UsuarioDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.mapper.AdapterMapper;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioCreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioDeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import br.com.fiap.tech.challenge_user.config.exceptions.http404.UsuarioNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "Usuários", description = "Contém os recursos de Cadastrar, Consultar, Atualizar e Deletar.")
@Slf4j
@RestController
@RequestMapping(path = {UsuarioController.URI_USUARIO})
@RequiredArgsConstructor
public class UsuarioController {

    protected static final String URI_USUARIO = "/api/v1/challenge-user/users";

    private final UsuarioCreateInputPort usuarioCreateInputPort;

    private final UsuarioFindByIdOutputPort usuarioFindByIdOutputPort;

    private final UsuarioDeleteByIdInputPort usuarioDeleteByIdInputPort;

    private final UsuarioUpdateInputPort usuarioUpdateInputPort;

    private final AdapterMapper adapterMapper;

    @PostMapping
    @Operation(summary = "Cadastrar", description = "Recurso para criar um novo Usuário.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Recurso cadastrado com sucesso.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDtoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição mal formulada.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403", description = "Cliente sem permissão para acessar recurso.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Conflito com regras de negócio.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "500", description = "Situação inesperada no servidor.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
    })
    public ResponseEntity<UsuarioDtoResponse> create(@RequestBody @Valid UsuarioDtoRequest usuarioDtoRequest) {

        log.info("UsuarioController - requisição feita no create: {}", usuarioDtoRequest);

        var response = Optional.ofNullable(usuarioDtoRequest)
                .map(adapterMapper::toUsuario)
                .map(usuarioCreateInputPort::create)
                .map(adapterMapper::toUsuarioDtoResponse)
                .orElseThrow();

        log.info("UsuarioController - requisição concluída no create: {}", response);

        return ResponseEntity
                .created(URI.create(URI_USUARIO + "/" + response.usuarioId()))
                .body(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UsuarioDtoResponse> findById(@PathVariable(name = "id") final UUID id) {

        log.info("UsuarioController - requisição feita no findById: {}", id);

        var response = usuarioFindByIdOutputPort.findById(id)
                .map(adapterMapper::toUsuarioDtoResponse)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        log.info("UsuarioController - requisição concluída no findById: {}", response);

        return ResponseEntity
                .ok()
                .body(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "id") final UUID id) {

        log.info("UsuarioController - requisição feita no deleteById: {}", id);

        Optional.ofNullable(id)
                .ifPresentOrElse(usuarioDeleteByIdInputPort::deleteById,
                        () -> {
                            throw new NoSuchElementException();
                        }
                );

        log.info("UsuarioController - requisição concluída no deleteById: {}", id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping
    public ResponseEntity<UsuarioDtoResponse> update(@RequestBody @Valid UsuarioUpdateDtoRequest usuarioUpdateDtoRequest) {

        log.info("UsuarioController - requisição feita no update: {}", usuarioUpdateDtoRequest);

        var response = Optional.ofNullable(usuarioUpdateDtoRequest)
                .map(adapterMapper::toUsuario)
                .map(usuarioUpdateInputPort::update)
                .map(adapterMapper::toUsuarioDtoResponse)
                .orElseThrow();

        log.info("UsuarioController - requisição concluída no update: {}", response);

        return ResponseEntity
                .ok()
                .body(response);
    }
}

