package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.adapter.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.adapter.mapper.AbstractUsuarioMapper;
import br.com.fiap.tech.challenge_user.application.core.domain.Cliente;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioDeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioCreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioDeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class Cliente2DeleteService extends AbstractUsuarioService<Cliente, ClienteEntity>
        implements UsuarioDeleteByIdInputPort<Cliente> {

    public Cliente2DeleteService(
            AbstractUsuarioMapper<?, ?, Cliente, ClienteEntity> mapper,
            UsuarioCreateOutputPort<ClienteEntity> createOutputPort,
            UsuarioFindByIdOutputPort<ClienteEntity> findByIdOutputPort,
            UsuarioDeleteOutputPort<ClienteEntity> deleteOutputPort) {
        super(mapper, createOutputPort, findByIdOutputPort, deleteOutputPort);
    }

    @Override
    public void deleteById(@NonNull final UUID id) {
        super.deleteById(id);
    }
}

