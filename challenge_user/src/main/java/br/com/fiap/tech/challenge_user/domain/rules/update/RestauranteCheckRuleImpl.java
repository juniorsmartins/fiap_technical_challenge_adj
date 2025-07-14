package br.com.fiap.tech.challenge_user.domain.rules.update;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.EntityMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.ProprietarioNotFoundException;
import br.com.fiap.tech.challenge_user.domain.models.Proprietario;
import br.com.fiap.tech.challenge_user.domain.models.Restaurante;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ProprietarioEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class RestauranteCheckRuleImpl implements RestauranteCheckRule {

    private final FindByIdOutputPort<ProprietarioEntity> findByIdOutputPort;

    private final EntityMapper<Proprietario, ProprietarioEntity> mapper;

    @Override
    public ProprietarioEntity checkProprietario(@NonNull final Restaurante restaurante) {

        var proprietarioId = restaurante.getProprietario().getUsuarioId();

        Object obj = findByIdOutputPort.findById(proprietarioId)
                .orElse(null);

        if (!(obj instanceof ProprietarioEntity)) {
            throw new ProprietarioNotFoundException(proprietarioId);
        }

        var proprietarioEntity = (ProprietarioEntity) obj;
        restaurante.setProprietario(mapper.toDomain(proprietarioEntity));

        return proprietarioEntity;
    }
}

