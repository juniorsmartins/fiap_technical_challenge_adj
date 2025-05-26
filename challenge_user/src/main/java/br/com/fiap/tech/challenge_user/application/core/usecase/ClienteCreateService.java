package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.adapter.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Cliente;
import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioCreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioCreateOutputPort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ClienteCreateService extends AbstractUsuarioCreateService<Cliente, ClienteEntity>
        implements UsuarioCreateInputPort<Cliente> {

    public ClienteCreateService(
            EntityMapper<Cliente, ClienteEntity> entityMapper,
            UsuarioCreateOutputPort<ClienteEntity> createOutputPort) {
        super(entityMapper, createOutputPort);
    }

    @Override
    public Cliente create(@NonNull final Cliente usuario) {
        return super.create(usuario);
    }
}

