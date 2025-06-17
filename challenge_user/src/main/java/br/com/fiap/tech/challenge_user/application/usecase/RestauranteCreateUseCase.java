package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.in.CreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.exception.http404.ProprietarioNotFoundException;
import br.com.fiap.tech.challenge_user.domain.model.Restaurante;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.infrastructure.entity.RestauranteEntity;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class RestauranteCreateUseCase extends AbstractCreateUseCase<Restaurante, RestauranteEntity>
        implements CreateInputPort<Restaurante> {

    private final FindByIdOutputPort<ProprietarioEntity> findByIdOutputPort;

    public RestauranteCreateUseCase(
            EntityMapper<Restaurante, RestauranteEntity> entityMapper,
            CreateOutputPort<RestauranteEntity> createOutputPort,
            FindByIdOutputPort<ProprietarioEntity> findByIdOutputPort) {
        super(entityMapper, createOutputPort);
        this.findByIdOutputPort = findByIdOutputPort;
    }

    @Override
    public Restaurante create(@NonNull final Restaurante restaurante) {

        this.checkProprietario(restaurante);
        return super.create(restaurante);
    }

    private void checkProprietario(Restaurante restaurante) {

        var proprietarioId = restaurante.getProprietario().getUsuarioId();

        findByIdOutputPort.findById(proprietarioId)
                .orElseThrow(() -> new ProprietarioNotFoundException(proprietarioId));
    }
}

