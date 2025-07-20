package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.EntityMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.in.UpdateInputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.models.Cliente;
import br.com.fiap.tech.challenge_user.domain.rules.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.domain.rules.update.EnderecoUpdateRule;
import br.com.fiap.tech.challenge_user.domain.rules.update.UsuarioUpdateRule;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ClienteDao;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public final class ClienteUpdateUseCase extends AbstractUpdateUseCase<Cliente, ClienteDao>
        implements UpdateInputPort<Cliente> {

    private final List<UsuarioRulesStrategy<Cliente>> rulesStrategy;

    private final UsuarioUpdateRule<Cliente, ClienteDao> usuarioUpdateRule;

    private final EnderecoUpdateRule<Cliente, ClienteDao> enderecoUpdateRule;

    public ClienteUpdateUseCase(
            EntityMapper<Cliente, ClienteDao> entityMapper,
            CreateOutputPort<ClienteDao> createOutputPort,
            FindByIdOutputPort<ClienteDao> findByIdOutputPort,
            UsuarioUpdateRule<Cliente, ClienteDao> usuarioUpdateRule,
            EnderecoUpdateRule<Cliente, ClienteDao> enderecoUpdateRule,
            List<UsuarioRulesStrategy<Cliente>> rulesStrategy) {
        super(entityMapper, createOutputPort, findByIdOutputPort);
        this.rulesStrategy = rulesStrategy;
        this.usuarioUpdateRule = usuarioUpdateRule;
        this.enderecoUpdateRule = enderecoUpdateRule;
    }

    @Override
    public Cliente update(@NonNull final UUID id, @NonNull Cliente usuario) {

        return super.update(id, usuario);
    }

    @Override
    public ClienteDao rulesUpdate(UUID id, Cliente domain, ClienteDao entity) {

        domain.setUsuarioId(id);
        rulesStrategy.forEach(rule -> rule.executar(domain));
        usuarioUpdateRule.updateUser(domain, entity);
        enderecoUpdateRule.updateAddress(domain, entity);
        return entity;
    }
}

