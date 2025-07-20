package br.com.fiap.tech.challenge_user.domain.rules.update;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.EntityMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.ProprietarioNotFoundException;
import br.com.fiap.tech.challenge_user.domain.entities.Proprietario;
import br.com.fiap.tech.challenge_user.domain.entities.Restaurante;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class RestauranteCheckRuleImpl implements RestauranteCheckRule {

    private final FindByIdOutputPort<ProprietarioDao> findByIdOutputPort;

    private final EntityMapper<Proprietario, ProprietarioDao> mapper;

    @Override
    public ProprietarioDao checkProprietario(@NonNull final Restaurante restaurante) {

        var proprietarioId = restaurante.getProprietario().getUsuarioId();

        Object obj = findByIdOutputPort.findById(proprietarioId)
                .orElse(null);

        if (!(obj instanceof ProprietarioDao)) {
            throw new ProprietarioNotFoundException(proprietarioId);
        }

        var proprietarioEntity = (ProprietarioDao) obj;
        restaurante.setProprietario(mapper.toDomain(proprietarioEntity));

        return proprietarioEntity;
    }
}

