package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.interfaces.in.UsuarioSenhaInputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ClienteDao;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteSenhaUseCase extends AbstractUsuarioSenhaUseCase<ClienteDao>
        implements UsuarioSenhaInputPort<ClienteDao> {

    public ClienteSenhaUseCase(
            FindByIdOutputPort<ClienteDao> findByIdOutputPort,
            CreateOutputPort<ClienteDao> createOutputPort) {
        super(findByIdOutputPort, createOutputPort);
    }

    @Override
    public void updatePassword(
            @NonNull final UUID usuarioId, @NonNull final String senhaAntiga, @NonNull final String senhaNova) {
        super.updatePassword(usuarioId, senhaAntiga, senhaNova);
    }
}

