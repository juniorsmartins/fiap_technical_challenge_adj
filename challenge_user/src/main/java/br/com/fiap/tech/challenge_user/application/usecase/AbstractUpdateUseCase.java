package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.EntityMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.RecursoNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class AbstractUpdateUseCase<T, E> {

    private final EntityMapper<T, E> entityMapper;

    private final CreateOutputPort<E> createOutputPort;

    private final FindByIdOutputPort<E> findByIdOutputPort;

    public T update(final UUID id, T usuario) {

        return findByIdOutputPort.findById(id)
                .map(entity -> this.rulesUpdate(id, usuario, entity))
                .map(createOutputPort::save)
                .map(entityMapper::toDomain)
                .orElseThrow(() -> {
                    log.error("AbstractUpdateService - Recurso não encontrado por id: {}.", id);
                    return new RecursoNotFoundException(id);
                });
    }

    public abstract E rulesUpdate(UUID id, T domain, E entity);
}

