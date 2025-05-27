package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.adapter.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Proprietario;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioDeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioDeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProprietarioDeleteService extends AbstractUsuarioDeleteService<ProprietarioEntity>
        implements UsuarioDeleteByIdInputPort<Proprietario> {

    public ProprietarioDeleteService(
            UsuarioFindByIdOutputPort<ProprietarioEntity> findByIdOutputPort,
            UsuarioDeleteOutputPort<ProprietarioEntity> deleteOutputPort) {
        super(findByIdOutputPort, deleteOutputPort);
    }

    @Override
    public void deleteById(@NonNull final UUID id) {
        super.deleteById(id);
    }
}

