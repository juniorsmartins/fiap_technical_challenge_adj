package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.adapter.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.adapter.mapper.AbstractUsuarioMapper;
import br.com.fiap.tech.challenge_user.application.core.domain.Cliente;
import br.com.fiap.tech.challenge_user.application.core.utils.UpdateUserRule;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioCreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioDeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ClienteUpdateService extends AbstractUsuarioService<Cliente, ClienteEntity>
        implements UsuarioUpdateInputPort<Cliente> {

    public ClienteUpdateService(
            AbstractUsuarioMapper<?, ?, ?, Cliente, ClienteEntity> mapper,
            UsuarioCreateOutputPort<ClienteEntity> createOutputPort,
            UsuarioFindByIdOutputPort<ClienteEntity> findByIdOutputPort,
            UsuarioDeleteOutputPort<ClienteEntity> deleteOutputPort,
            UpdateUserRule<Cliente, ClienteEntity> updateUserRule) {
        super(mapper, createOutputPort, findByIdOutputPort, deleteOutputPort, updateUserRule);
    }

    @Override
    public Cliente update(@NonNull Cliente usuario) {
        return super.update(usuario);
    }
}

