package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.EntityMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.in.CreateInputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.domain.models.Proprietario;
import br.com.fiap.tech.challenge_user.domain.rules.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ProprietarioEntity;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProprietarioCreateUseCase extends AbstractCreateUseCase<Proprietario, ProprietarioEntity>
        implements CreateInputPort<Proprietario> {

    private final List<UsuarioRulesStrategy<Proprietario>> rulesStrategy;

    public ProprietarioCreateUseCase(
            EntityMapper<Proprietario, ProprietarioEntity> entityMapper,
            CreateOutputPort<ProprietarioEntity> createOutputPort,
            List<UsuarioRulesStrategy<Proprietario>> rulesStrategy) {
        super(entityMapper, createOutputPort);
        this.rulesStrategy = rulesStrategy;

    }

    @Override
    public Proprietario create(@NonNull final Proprietario usuario) {

        rulesStrategy.forEach(rule -> rule.executar(usuario));
        return super.create(usuario);
    }
}

