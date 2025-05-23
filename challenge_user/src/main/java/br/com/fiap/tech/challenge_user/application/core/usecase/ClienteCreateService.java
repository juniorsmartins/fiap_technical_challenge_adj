package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.adapter.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.adapter.mapper.AbstractUsuarioMapper;
import br.com.fiap.tech.challenge_user.application.core.domain.Cliente;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioCreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioCreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioDeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ClienteCreateService extends AbstractUsuarioService<Cliente, ClienteEntity>
        implements UsuarioCreateInputPort<Cliente> {

    public ClienteCreateService(
            AbstractUsuarioMapper<?, ?, ?, Cliente, ClienteEntity> mapper,
            UsuarioCreateOutputPort<ClienteEntity> createOutputPort,
            UsuarioFindByIdOutputPort<ClienteEntity> findByIdOutputPort,
            UsuarioDeleteOutputPort<ClienteEntity> deleteOutputPort) {
        super(mapper, createOutputPort, findByIdOutputPort, deleteOutputPort);
    }

    @Override
    public Cliente create(@NonNull final Cliente usuario) {
        return super.create(usuario);
    }
}
