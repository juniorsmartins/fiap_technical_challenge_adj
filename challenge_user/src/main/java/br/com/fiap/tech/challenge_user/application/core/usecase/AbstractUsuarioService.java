package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.adapter.mapper.AbstractUsuarioMapper;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioCreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioDeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import br.com.fiap.tech.challenge_user.config.exceptions.http404.UsuarioNotFoundException;
import br.com.fiap.tech.challenge_user.config.exceptions.http500.InternalServerProblemException;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class AbstractUsuarioService<T, E> {

    private final AbstractUsuarioMapper<?, ?, T, E> mapper;

    private final UsuarioCreateOutputPort<E> createOutputPort;

    private final UsuarioFindByIdOutputPort<E> findByIdOutputPort;

    private final UsuarioDeleteOutputPort<E> deleteOutputPort;

    public T create(@NotNull final T usuario) {

        return Optional.of(usuario)
                .map(mapper::toUsuarioEntity)
                .map(createOutputPort::save)
                .map(mapper::toUsuarioOut)
                .orElseThrow(() -> {
                    log.error("AbstractUsuarioService - Erro interno do servidor no método create.");
                    return new InternalServerProblemException();
                });
    }

    public void deleteById(@NonNull final UUID id) {

        findByIdOutputPort.findById(id)
                .ifPresentOrElse(deleteOutputPort::delete, () -> {
                    log.error("AbstractUsuarioService - Usuário não encontrado por id: {}.", id);
                    throw new UsuarioNotFoundException(id);
                });
    }
}

