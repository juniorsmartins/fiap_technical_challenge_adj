package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.domain.model.Cliente;
import br.com.fiap.tech.challenge_user.domain.rule.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.domain.rule.update.EnderecoUpdateRule;
import br.com.fiap.tech.challenge_user.domain.rule.update.UsuarioUpdateRule;
import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.in.UsuarioUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteUpdateService extends AbstractUsuarioUpdateService<Cliente, ClienteEntity>
        implements UsuarioUpdateInputPort<Cliente> {

    public ClienteUpdateService(
            EntityMapper<Cliente, ClienteEntity> entityMapper,
            CreateOutputPort<ClienteEntity> createOutputPort,
            FindByIdOutputPort<ClienteEntity> findByIdOutputPort,
            UsuarioUpdateRule<Cliente, ClienteEntity> usuarioUpdateRule,
            EnderecoUpdateRule<Cliente, ClienteEntity> enderecoUpdateRule,
            List<UsuarioRulesStrategy<Cliente>> rulesStrategy) {
        super(entityMapper, createOutputPort, findByIdOutputPort, usuarioUpdateRule, enderecoUpdateRule, rulesStrategy);
    }

    @Override
    public Cliente update(@NonNull UUID id, @NonNull Cliente usuario) {
        return super.update(id, usuario);
    }
}

