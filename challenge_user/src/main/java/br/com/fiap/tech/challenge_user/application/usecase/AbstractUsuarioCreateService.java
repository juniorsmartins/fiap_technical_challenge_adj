package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.domain.rule.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.infrastructure.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.domain.model.Usuario;
import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioCreateOutputPort;
import br.com.fiap.tech.challenge_user.domain.exception.http500.InternalServerProblemException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class AbstractUsuarioCreateService<T extends Usuario, E extends UsuarioEntity> {

    private final EntityMapper<T, E> entityMapper;

    private final UsuarioCreateOutputPort<E> createOutputPort;

    private final List<UsuarioRulesStrategy<T>> rulesStrategy;

    public T create(@NotNull final T usuario) {

        return Optional.of(usuario)
                .map(this::rules)
                .map(entityMapper::toEntity)
                .map(createOutputPort::save)
                .map(entityMapper::toDomain)
                .orElseThrow(() -> {
                    log.error("AbstractUsuarioCreateService - Erro interno do servidor no método create.");
                    return new InternalServerProblemException();
                });
    }

    private T rules(T usuario) {
        rulesStrategy.forEach(rule -> rule.executar(usuario));
        return usuario;
    }
}

