package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.domain.rule.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.domain.model.Cliente;
import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.in.CreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioCreateOutputPort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteCreateService extends AbstractUsuarioCreateService<Cliente, ClienteEntity>
        implements CreateInputPort<Cliente> {

    public ClienteCreateService(
            EntityMapper<Cliente, ClienteEntity> entityMapper,
            UsuarioCreateOutputPort<ClienteEntity> createOutputPort,
            List<UsuarioRulesStrategy<Cliente>> rulesStrategy) {
        super(entityMapper, createOutputPort, rulesStrategy);
    }

    @Override
    public Cliente create(@NonNull final Cliente usuario) {
        return super.create(usuario);
    }
}

