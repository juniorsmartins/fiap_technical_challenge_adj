package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.in.CreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.domain.model.Restaurante;
import br.com.fiap.tech.challenge_user.domain.rule.update.RestauranteUpdateRule;
import br.com.fiap.tech.challenge_user.infrastructure.entity.RestauranteEntity;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class RestauranteCreateUseCase extends AbstractCreateUseCase<Restaurante, RestauranteEntity>
        implements CreateInputPort<Restaurante> {

    private final RestauranteUpdateRule restauranteUpdateRule;

    public RestauranteCreateUseCase(
            EntityMapper<Restaurante, RestauranteEntity> entityMapper,
            CreateOutputPort<RestauranteEntity> createOutputPort,
            RestauranteUpdateRule restauranteUpdateRule) {
        super(entityMapper, createOutputPort);
        this.restauranteUpdateRule = restauranteUpdateRule;
    }

    @Override
    public Restaurante create(@NonNull final Restaurante restaurante) {

        restauranteUpdateRule.checkProprietario(restaurante);
        return super.create(restaurante);
    }
}

