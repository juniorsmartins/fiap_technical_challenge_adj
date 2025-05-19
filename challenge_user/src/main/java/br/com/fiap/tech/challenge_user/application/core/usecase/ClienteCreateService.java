package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.application.core.domain.Cliente;
import br.com.fiap.tech.challenge_user.application.core.mapper.ApplicationMapper;
import br.com.fiap.tech.challenge_user.application.port.input.ClienteCreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.ClienteCreateOutputPort;
import br.com.fiap.tech.challenge_user.config.exceptions.http500.InternalServerProblemException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteCreateService implements ClienteCreateInputPort {

    private final ClienteCreateOutputPort clienteCreateOutputPort;

    private final ApplicationMapper applicationMapper;

    @Override
    public Cliente create(@NotNull final Cliente cliente) {

        log.info("ClienteCreateService - entrada no service de create: {}", cliente);

        var usuarioCreated = Optional.of(cliente)
                .map(applicationMapper::toClienteEntity)
                .map(clienteCreateOutputPort::save)
                .map(applicationMapper::toCliente)
                .orElseThrow(() -> {
                    log.error("ClienteCreateService - Erro interno do servidor no método create.");
                    return new InternalServerProblemException();
                });

        log.info("ClienteCreateService - concluído serviço de create: {}", usuarioCreated);

        return usuarioCreated;
    }
}

