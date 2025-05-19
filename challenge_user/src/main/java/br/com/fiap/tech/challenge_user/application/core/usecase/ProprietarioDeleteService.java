package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.application.port.input.ProprietarioDeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.ProprietarioDeleteByIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProprietarioDeleteService implements ProprietarioDeleteByIdInputPort {

    private final ProprietarioDeleteByIdOutputPort proprietarioDeleteByIdOutputPort;

    @Override
    public void deleteById(@NonNull final UUID id) {

        log.info("ProprietarioDeleteService - entrada no service de deleteById: {}", id);

        proprietarioDeleteByIdOutputPort.deleteById(id);

        log.info("ProprietarioDeleteService - concluído serviço de deleteById: {}", id);
    }
}

