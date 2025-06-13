package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.in.CreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.domain.model.Restaurante;
import br.com.fiap.tech.challenge_user.infrastructure.entity.RestauranteEntity;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class RestauranteCreateService extends AbstractCreateService<Restaurante, RestauranteEntity>
        implements CreateInputPort<Restaurante> {

    public RestauranteCreateService(
            EntityMapper<Restaurante, RestauranteEntity> entityMapper,
            CreateOutputPort<RestauranteEntity> createOutputPort) {
        super(entityMapper, createOutputPort);
    }

    @Override
    public Restaurante create(@NonNull final Restaurante restaurante) {

        return super.create(restaurante);
    }
}

