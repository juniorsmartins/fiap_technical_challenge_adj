package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.application.port.input.ClienteDeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.ClienteDeleteByIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteDeleteService implements ClienteDeleteByIdInputPort {

    private final ClienteDeleteByIdOutputPort clienteDeleteByIdOutputPort;

    @Override
    public void deleteById(@NonNull final UUID id) {

        log.info("ClienteDeleteService - entrada no service de deleteById: {}", id);

        clienteDeleteByIdOutputPort.deleteById(id);

        log.info("ClienteDeleteService - concluído serviço de deleteById: {}", id);
    }
}

