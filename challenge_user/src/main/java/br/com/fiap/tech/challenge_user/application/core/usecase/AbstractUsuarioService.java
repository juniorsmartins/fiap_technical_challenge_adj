package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.adapter.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.adapter.mapper.AbstractUsuarioMapper;
import br.com.fiap.tech.challenge_user.application.core.domain.Usuario;
import br.com.fiap.tech.challenge_user.application.core.utils.UsuarioUpdateUtils;
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
public abstract class AbstractUsuarioService<T extends Usuario, E extends UsuarioEntity> {

    private final AbstractUsuarioMapper<?, ?, ?, T, E> mapper;

    private final UsuarioCreateOutputPort<E> createOutputPort;

    private final UsuarioFindByIdOutputPort<E> findByIdOutputPort;

    private final UsuarioDeleteOutputPort<E> deleteOutputPort;

    private final UsuarioUpdateUtils<T, E> updateUser;

    public T create(@NotNull final T usuario) {

        return Optional.of(usuario)
                .map(mapper::toEntity)
                .map(createOutputPort::save)
                .map(mapper::toDomainOut)
                .orElseThrow(() -> {
                    log.error("AbstractUsuarioService - Erro interno do servidor no método create.");
                    return new InternalServerProblemException();
                });
    }

    public T update(@NonNull T usuario) {

        var id = usuario.getUsuarioId();

        return findByIdOutputPort.findById(id)
                .map(entity -> updateUser.up(usuario, entity))
                .map(createOutputPort::save)
                .map(mapper::toDomainOut)
                .orElseThrow(() -> {
                    log.error("AbstractUsuarioService - Usuário não encontrado por id: {}.", id);
                    return new UsuarioNotFoundException(id);
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

