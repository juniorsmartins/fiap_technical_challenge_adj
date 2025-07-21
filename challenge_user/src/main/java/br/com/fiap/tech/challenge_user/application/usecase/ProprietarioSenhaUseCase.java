package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.interfaces.in.UsuarioSenhaInputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProprietarioSenhaUseCase extends AbstractUsuarioSenhaUseCase<ProprietarioDao>
        implements UsuarioSenhaInputPort<ProprietarioDao> {

    public ProprietarioSenhaUseCase(
            FindByIdOutputPort<ProprietarioDao> findByIdOutputPort,
            CreateOutputPort<ProprietarioDao> createOutputPort) {
        super(findByIdOutputPort, createOutputPort);
    }

    @Override
    public void updatePassword(
            @NonNull final UUID usuarioId, @NonNull final String senhaAntiga, @NonNull final String senhaNova) {
        super.updatePassword(usuarioId, senhaAntiga, senhaNova);
    }
}

