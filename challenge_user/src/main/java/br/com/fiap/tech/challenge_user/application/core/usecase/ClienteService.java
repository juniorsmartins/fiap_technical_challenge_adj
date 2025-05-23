package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.adapter.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.adapter.mapper.AbstractUsuarioMapper;
import br.com.fiap.tech.challenge_user.application.core.domain.Cliente;
import br.com.fiap.tech.challenge_user.application.core.utils.UsuarioUpdateUtils;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioCreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioDeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioCreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioDeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteService extends AbstractUsuarioService<Cliente, ClienteEntity>
        implements UsuarioCreateInputPort<Cliente>, UsuarioUpdateInputPort<Cliente>, UsuarioDeleteByIdInputPort<Cliente> {

    public ClienteService(
            AbstractUsuarioMapper<?, ?, ?, Cliente, ClienteEntity> mapper,
            UsuarioCreateOutputPort<ClienteEntity> createOutputPort,
            UsuarioFindByIdOutputPort<ClienteEntity> findByIdOutputPort,
            UsuarioDeleteOutputPort<ClienteEntity> deleteOutputPort,
            UsuarioUpdateUtils<Cliente, ClienteEntity> usuarioUpdateUtils) {
        super(mapper, createOutputPort, findByIdOutputPort, deleteOutputPort, usuarioUpdateUtils);
    }

    @Override
    public Cliente create(@NonNull final Cliente usuario) {
        return super.create(usuario);
    }

    @Override
    public Cliente update(@NonNull Cliente usuario) {
        return super.update(usuario);
    }

    @Override
    public void deleteById(@NonNull final UUID id) {
        super.deleteById(id);
    }
}
