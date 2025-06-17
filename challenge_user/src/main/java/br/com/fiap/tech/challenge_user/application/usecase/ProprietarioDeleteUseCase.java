package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.domain.model.Proprietario;
import br.com.fiap.tech.challenge_user.application.port.in.DeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProprietarioDeleteUseCase extends AbstractDeleteUseCase<ProprietarioEntity>
        implements DeleteByIdInputPort<Proprietario> {

    public ProprietarioDeleteUseCase(
            FindByIdOutputPort<ProprietarioEntity> findByIdOutputPort,
            DeleteOutputPort<ProprietarioEntity> deleteOutputPort) {
        super(findByIdOutputPort, deleteOutputPort);
    }

    @Override
    public void deleteById(@NonNull final UUID id) {
        super.deleteById(id);
    }
}

