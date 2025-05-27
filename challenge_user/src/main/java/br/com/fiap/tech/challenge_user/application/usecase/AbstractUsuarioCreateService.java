package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.application.domain.model.Usuario;
import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioCreateOutputPort;
import br.com.fiap.tech.challenge_user.application.domain.exception.http500.InternalServerProblemException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class AbstractUsuarioCreateService<T extends Usuario, E extends UsuarioEntity> {

    private final EntityMapper<T, E> entityMapper;

    private final UsuarioCreateOutputPort<E> createOutputPort;

    public T create(@NotNull final T usuario) {

        return Optional.of(usuario)
                .map(entityMapper::toEntity)
                .map(createOutputPort::save)
                .map(entityMapper::toDomain)
                .orElseThrow(() -> {
                    log.error("AbstractUsuarioCreateService - Erro interno do servidor no método create.");
                    return new InternalServerProblemException();
                });
    }
}

