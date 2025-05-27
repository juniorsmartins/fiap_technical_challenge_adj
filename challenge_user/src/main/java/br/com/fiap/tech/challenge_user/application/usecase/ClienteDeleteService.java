package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.application.domain.model.Cliente;
import br.com.fiap.tech.challenge_user.application.port.in.UsuarioDeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioDeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioFindByIdOutputPort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteDeleteService extends AbstractUsuarioDeleteService<ClienteEntity>
        implements UsuarioDeleteByIdInputPort<Cliente> {

    public ClienteDeleteService(
            UsuarioFindByIdOutputPort<ClienteEntity> findByIdOutputPort,
            UsuarioDeleteOutputPort<ClienteEntity> deleteOutputPort) {
        super(findByIdOutputPort, deleteOutputPort);
    }

    @Override
    public void deleteById(@NonNull final UUID id) {
        super.deleteById(id);
    }
}

