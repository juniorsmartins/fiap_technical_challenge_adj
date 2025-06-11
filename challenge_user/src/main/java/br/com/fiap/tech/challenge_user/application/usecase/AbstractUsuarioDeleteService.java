package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioDeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioFindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.exception.http404.UsuarioNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class AbstractUsuarioDeleteService<E extends UsuarioEntity> {

    private final UsuarioFindByIdOutputPort<E> findByIdOutputPort;

    private final UsuarioDeleteOutputPort<E> deleteOutputPort;

    public void deleteById(@NonNull final UUID id) {

        findByIdOutputPort.findById(id)
                .ifPresentOrElse(deleteOutputPort::delete, () -> {
                    log.error("AbstractUsuarioDeleteService - Usuário não encontrado por id: {}.", id);
                    throw new UsuarioNotFoundException(id);
                });
    }
}

