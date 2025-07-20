package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.EntityMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.in.UpdateInputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.models.Restaurante;
import br.com.fiap.tech.challenge_user.domain.rules.update.RestauranteCheckRule;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.RestauranteDao;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public final class RestauranteUpdateUseCase extends AbstractUpdateUseCase<Restaurante, RestauranteDao>
        implements UpdateInputPort<Restaurante> {

    private final RestauranteCheckRule restauranteCheckRule;

    public RestauranteUpdateUseCase(
            EntityMapper<Restaurante, RestauranteDao> entityMapper,
            CreateOutputPort<RestauranteDao> createOutputPort,
            FindByIdOutputPort<RestauranteDao> findByIdOutputPort,
            RestauranteCheckRule restauranteCheckRule) {
        super(entityMapper, createOutputPort, findByIdOutputPort);
        this.restauranteCheckRule = restauranteCheckRule;
    }

    @Override
    public Restaurante update(@NonNull final UUID id, @NonNull Restaurante domain) {

        return super.update(id, domain);
    }

    @Override
    public RestauranteDao rulesUpdate(UUID id, Restaurante domain, RestauranteDao entity) {

        domain.setRestauranteId(id);
        var proprietarioEntity = restauranteCheckRule.checkProprietario(domain);

        BeanUtils.copyProperties(domain, entity, "restauranteId", "proprietario");
        BeanUtils.copyProperties(domain.getEndereco(), entity.getEndereco(), "enderecoId");

        entity.setProprietario(proprietarioEntity);
        return entity;
    }
}

