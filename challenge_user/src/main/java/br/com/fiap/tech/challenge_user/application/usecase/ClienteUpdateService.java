package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.application.domain.model.Cliente;
import br.com.fiap.tech.challenge_user.application.domain.rule.update.EnderecoUpdateRule;
import br.com.fiap.tech.challenge_user.application.domain.rule.update.UsuarioUpdateRule;
import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.in.UsuarioUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioCreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioFindByIdOutputPort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteUpdateService extends AbstractUsuarioUpdateService<Cliente, ClienteEntity>
        implements UsuarioUpdateInputPort<Cliente> {

    public ClienteUpdateService(
            EntityMapper<Cliente, ClienteEntity> entityMapper,
            UsuarioCreateOutputPort<ClienteEntity> createOutputPort,
            UsuarioFindByIdOutputPort<ClienteEntity> findByIdOutputPort,
            UsuarioUpdateRule<Cliente, ClienteEntity> usuarioUpdateRule,
            EnderecoUpdateRule<Cliente, ClienteEntity> enderecoUpdateRule) {
        super(entityMapper, createOutputPort, findByIdOutputPort, usuarioUpdateRule, enderecoUpdateRule);
    }

    @Override
    public Cliente update(@NonNull UUID id, @NonNull Cliente usuario) {
        return super.update(id, usuario);
    }
}

