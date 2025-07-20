package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.EntityMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.in.CreateInputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.domain.entities.Restaurante;
import br.com.fiap.tech.challenge_user.domain.rules.update.RestauranteCheckRule;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.RestauranteDao;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class RestauranteCreateUseCase extends AbstractCreateUseCase<Restaurante, RestauranteDao>
        implements CreateInputPort<Restaurante> {

    private final RestauranteCheckRule restauranteCheckRule;

    public RestauranteCreateUseCase(
            EntityMapper<Restaurante, RestauranteDao> entityMapper,
            CreateOutputPort<RestauranteDao> createOutputPort,
            RestauranteCheckRule restauranteCheckRule) {
        super(entityMapper, createOutputPort);
        this.restauranteCheckRule = restauranteCheckRule;
    }

    @Override
    public Restaurante create(@NonNull final Restaurante restaurante) {

        restauranteCheckRule.checkProprietario(restaurante);
        return super.create(restaurante);
    }
}

