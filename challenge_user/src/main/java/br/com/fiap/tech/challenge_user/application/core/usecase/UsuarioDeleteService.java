package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.application.port.input.UsuarioDeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioDeleteByIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioDeleteService implements UsuarioDeleteByIdInputPort {

    private final UsuarioDeleteByIdOutputPort usuarioDeleteByIdOutputPort;

    @Override
    public void deleteById(@NonNull final UUID id) {

        log.info("UsuarioDeleteService - entrada no service de deleteById: {}", id);

        usuarioDeleteByIdOutputPort.deleteById(id);

        log.info("UsuarioDeleteService - concluído serviço de deleteById: {}", id);
    }
}

