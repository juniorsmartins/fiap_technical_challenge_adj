package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.in.CreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.domain.model.Proprietario;
import br.com.fiap.tech.challenge_user.domain.rule.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
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

