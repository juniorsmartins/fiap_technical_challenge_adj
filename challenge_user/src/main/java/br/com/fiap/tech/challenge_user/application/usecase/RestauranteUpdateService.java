package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.in.UpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.exception.http404.RestauranteNotFoundException;
import br.com.fiap.tech.challenge_user.domain.model.Restaurante;
import br.com.fiap.tech.challenge_user.infrastructure.entity.RestauranteEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestauranteUpdateService implements UpdateInputPort<Restaurante> {

    private final EntityMapper<Restaurante, RestauranteEntity> entityMapper;

    private final CreateOutputPort<RestauranteEntity> createOutputPort;

    private final FindByIdOutputPort<RestauranteEntity> findByIdOutputPort;

    @Override
    public Restaurante update(@NonNull final UUID id, @NonNull Restaurante domain) {

        return findByIdOutputPort.findById(id)
                .map(entity -> this.updateRestaurant(domain, entity))
                .map(createOutputPort::save)
                .map(entityMapper::toDomain)
                .orElseThrow(() -> {
                    log.error("RestauranteUpdateService - Restaurante não encontrado por id: {}.", id);
                    return new RestauranteNotFoundException(id);
                });
    }

    private RestauranteEntity updateRestaurant(Restaurante domain, RestauranteEntity entity) {
        BeanUtils.copyProperties(domain, entity, "restauranteId");
        BeanUtils.copyProperties(domain.getEndereco(), entity.getEndereco(), "enderecoId");
        return entity;
    }
}

