package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.DaoPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http500.InternalServerProblemException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class AbstractCreateUseCase<T, E> {

    private final DaoPresenter<T, E> daoPresenter;

    private final CreateOutputPort<E> createOutputPort;

    public T create(final T usuario) {

        return Optional.of(usuario)
                .map(daoPresenter::toEntity)
                .map(createOutputPort::save)
                .map(daoPresenter::toDomain)
                .orElseThrow(() -> {
                    log.error("AbstractCreateService - Erro interno do servidor no método create.");
                    return new InternalServerProblemException();
                });
    }
}

