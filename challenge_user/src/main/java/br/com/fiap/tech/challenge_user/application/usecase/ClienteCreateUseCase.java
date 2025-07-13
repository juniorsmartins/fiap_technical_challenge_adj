package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.presenters.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.in.CreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.domain.model.Cliente;
import br.com.fiap.tech.challenge_user.domain.rule.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteCreateUseCase extends AbstractCreateUseCase<Cliente, ClienteEntity>
        implements CreateInputPort<Cliente> {

    private final List<UsuarioRulesStrategy<Cliente>> rulesStrategy;

    public ClienteCreateUseCase(
            EntityMapper<Cliente, ClienteEntity> entityMapper,
            CreateOutputPort<ClienteEntity> createOutputPort,
            List<UsuarioRulesStrategy<Cliente>> rulesStrategy) {
        super(entityMapper, createOutputPort);
        this.rulesStrategy = rulesStrategy;
    }

    @Override
    public Cliente create(@NonNull final Cliente usuario) {

        rulesStrategy.forEach(rule -> rule.executar(usuario));
        return super.create(usuario);
    }
}

