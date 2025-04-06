package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.application.core.domain.Usuario;
import br.com.fiap.tech.challenge_user.application.core.mapper.ApplicationMapper;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioCreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioCreateOutputPort;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioCreateService implements UsuarioCreateInputPort {

    private final UsuarioCreateOutputPort usuarioCreateOutputPort;

    private final ApplicationMapper applicationMapper;

    @Override
    public Usuario create(@NotNull final Usuario usuario) {

        log.info("UsuarioCreateService - entrada no service de create: {}", usuario);

        var usuarioCreated = Optional.of(usuario)
                .map(applicationMapper::toUsuarioEntity)
                .map(usuarioCreateOutputPort::save)
                .map(applicationMapper::toUsuario)
                .orElseThrow();

        log.info("UsuarioCreateService - concluído serviço de create: {}", usuarioCreated);

        return usuarioCreated;
    }
}

