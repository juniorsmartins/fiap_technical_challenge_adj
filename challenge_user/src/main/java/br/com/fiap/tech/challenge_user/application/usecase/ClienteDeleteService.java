package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.domain.model.Cliente;
import br.com.fiap.tech.challenge_user.application.port.in.DeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteDeleteService extends AbstractDeleteService<ClienteEntity>
        implements DeleteByIdInputPort<Cliente> {

    public ClienteDeleteService(
            FindByIdOutputPort<ClienteEntity> findByIdOutputPort,
            DeleteOutputPort<ClienteEntity> deleteOutputPort) {
        super(findByIdOutputPort, deleteOutputPort);
    }

    @Override
    public void deleteById(@NonNull final UUID id) {
        super.deleteById(id);
    }
}

