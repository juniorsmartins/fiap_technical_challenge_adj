package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.in.UsuarioCreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.RestauranteCreateOutputPort;
import br.com.fiap.tech.challenge_user.domain.exception.http500.InternalServerProblemException;
import br.com.fiap.tech.challenge_user.domain.model.Restaurante;
import br.com.fiap.tech.challenge_user.infrastructure.entity.RestauranteEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestauranteCreateService implements UsuarioCreateInputPort<Restaurante> {

    private final EntityMapper<Restaurante, RestauranteEntity> entityMapper;

    private final RestauranteCreateOutputPort createOutputPort;

    @Override
    public Restaurante create(@NonNull final Restaurante restaurante) {

        return Optional.of(restaurante)
                .map(entityMapper::toEntity)
                .map(createOutputPort::save)
                .map(entityMapper::toDomain)
                .orElseThrow(() -> {
                    log.error("RestauranteCreateService - Erro interno do servidor no método create.");
                    return new InternalServerProblemException();
                });
    }
}

