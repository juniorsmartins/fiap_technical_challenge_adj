package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.presenters.EntityMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.in.UpdateInputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.model.Restaurante;
import br.com.fiap.tech.challenge_user.domain.rule.update.RestauranteCheckRule;
import br.com.fiap.tech.challenge_user.infrastructure.entity.RestauranteEntity;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public final class RestauranteUpdateUseCase extends AbstractUpdateUseCase<Restaurante, RestauranteEntity>
        implements UpdateInputPort<Restaurante> {

    private final RestauranteCheckRule restauranteCheckRule;

    public RestauranteUpdateUseCase(
            EntityMapper<Restaurante, RestauranteEntity> entityMapper,
            CreateOutputPort<RestauranteEntity> createOutputPort,
            FindByIdOutputPort<RestauranteEntity> findByIdOutputPort,
            RestauranteCheckRule restauranteCheckRule) {
        super(entityMapper, createOutputPort, findByIdOutputPort);
        this.restauranteCheckRule = restauranteCheckRule;
    }

    @Override
    public Restaurante update(@NonNull final UUID id, @NonNull Restaurante domain) {

        return super.update(id, domain);
    }

    @Override
    public RestauranteEntity rulesUpdate(UUID id, Restaurante domain, RestauranteEntity entity) {

        domain.setRestauranteId(id);
        var proprietarioEntity = restauranteCheckRule.checkProprietario(domain);

        BeanUtils.copyProperties(domain, entity, "restauranteId", "proprietario");
        BeanUtils.copyProperties(domain.getEndereco(), entity.getEndereco(), "enderecoId");

        entity.setProprietario(proprietarioEntity);
        return entity;
    }
}

