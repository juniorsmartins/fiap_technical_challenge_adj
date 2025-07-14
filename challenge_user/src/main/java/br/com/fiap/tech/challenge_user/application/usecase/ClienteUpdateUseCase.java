package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.presenters.EntityMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.in.UpdateInputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.model.Cliente;
import br.com.fiap.tech.challenge_user.domain.rule.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.domain.rule.update.EnderecoUpdateRule;
import br.com.fiap.tech.challenge_user.domain.rule.update.UsuarioUpdateRule;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public final class ClienteUpdateUseCase extends AbstractUpdateUseCase<Cliente, ClienteEntity>
        implements UpdateInputPort<Cliente> {

    private final List<UsuarioRulesStrategy<Cliente>> rulesStrategy;

    private final UsuarioUpdateRule<Cliente, ClienteEntity> usuarioUpdateRule;

    private final EnderecoUpdateRule<Cliente, ClienteEntity> enderecoUpdateRule;

    public ClienteUpdateUseCase(
            EntityMapper<Cliente, ClienteEntity> entityMapper,
            CreateOutputPort<ClienteEntity> createOutputPort,
            FindByIdOutputPort<ClienteEntity> findByIdOutputPort,
            UsuarioUpdateRule<Cliente, ClienteEntity> usuarioUpdateRule,
            EnderecoUpdateRule<Cliente, ClienteEntity> enderecoUpdateRule,
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
    public ClienteEntity rulesUpdate(UUID id, Cliente domain, ClienteEntity entity) {

        domain.setUsuarioId(id);
        rulesStrategy.forEach(rule -> rule.executar(domain));
        usuarioUpdateRule.updateUser(domain, entity);
        enderecoUpdateRule.updateAddress(domain, entity);
        return entity;
    }
}

