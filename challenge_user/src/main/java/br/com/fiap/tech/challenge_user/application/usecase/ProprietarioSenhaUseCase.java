package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.port.in.UsuarioSenhaInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProprietarioSenhaUseCase extends AbstractUsuarioSenhaUseCase<ProprietarioEntity>
        implements UsuarioSenhaInputPort<ProprietarioEntity> {

    public ProprietarioSenhaUseCase(
            FindByIdOutputPort<ProprietarioEntity> findByIdOutputPort,
            CreateOutputPort<ProprietarioEntity> createOutputPort) {
        super(findByIdOutputPort, createOutputPort);
    }

    @Override
    public void updatePassword(
            @NonNull final UUID usuarioId, @NonNull final String senhaAntiga, @NonNull final String senhaNova) {
        super.updatePassword(usuarioId, senhaAntiga, senhaNova);
    }
}

