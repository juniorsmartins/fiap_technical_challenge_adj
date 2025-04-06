package br.com.fiap.tech.challenge_user.adapter.controller;

import br.com.fiap.tech.challenge_user.adapter.dto.UsuarioDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.UsuarioDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.mapper.CentralMapper;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioCreateInputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = {UsuarioController.URI_USUARIO})
@RequiredArgsConstructor
public class UsuarioController {

    protected static final String URI_USUARIO = "/api/v1/challenge-user/user";

    private final UsuarioCreateInputPort usuarioCreateInputPort;

    private final CentralMapper centralMapper;

    @PostMapping
    public ResponseEntity<UsuarioDtoResponse> create(@RequestBody UsuarioDtoRequest usuarioDtoRequest) {

        log.info("UserController - requisição feita no create: {}", usuarioDtoRequest);

        var response = Optional.ofNullable(usuarioDtoRequest)
                .map(centralMapper::toUsuario)
                .map(usuarioCreateInputPort::create)
                .map(centralMapper::toUsuarioDtoResponse)
                .orElseThrow();

        log.info("UserController - requisição concluída no create: {}", response);

        return ResponseEntity
                .created(URI.create(URI_USUARIO + "/" + response.usuarioId()))
                .body(response);
    }
}

