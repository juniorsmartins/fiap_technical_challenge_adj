package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.interfaces.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.RecursoNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class AbstractDeleteUseCase<E> {

    private final FindByIdOutputPort<E> findByIdOutputPort;

    private final DeleteOutputPort<E> deleteOutputPort;

    public void deleteById(final UUID id) {

        findByIdOutputPort.findById(id)
                .ifPresentOrElse(deleteOutputPort::delete, () -> {
                    log.error("AbstractDeleteService - Recurso não encontrado por id: {}.", id);
                    throw new RecursoNotFoundException(id);
                });
    }
}

