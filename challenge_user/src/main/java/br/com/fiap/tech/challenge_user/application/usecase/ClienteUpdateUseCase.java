package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.domain.model.Cliente;
import br.com.fiap.tech.challenge_user.domain.rule.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.domain.rule.update.EnderecoCheckRule;
import br.com.fiap.tech.challenge_user.domain.rule.update.UsuarioCheckRule;
import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.in.UpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteUpdateUseCase extends AbstractUsuarioUpdateUseCase<Cliente, ClienteEntity>
        implements UpdateInputPort<Cliente> {

    public ClienteUpdateUseCase(
            EntityMapper<Cliente, ClienteEntity> entityMapper,
            CreateOutputPort<ClienteEntity> createOutputPort,
            FindByIdOutputPort<ClienteEntity> findByIdOutputPort,
            UsuarioCheckRule<Cliente, ClienteEntity> usuarioCheckRule,
            EnderecoCheckRule<Cliente, ClienteEntity> enderecoCheckRule,
            List<UsuarioRulesStrategy<Cliente>> rulesStrategy) {
        super(entityMapper, createOutputPort, findByIdOutputPort, usuarioCheckRule, enderecoCheckRule, rulesStrategy);
    }

    @Override
    public Cliente update(@NonNull final UUID id, @NonNull Cliente usuario) {
        return super.update(id, usuario);
    }
}

