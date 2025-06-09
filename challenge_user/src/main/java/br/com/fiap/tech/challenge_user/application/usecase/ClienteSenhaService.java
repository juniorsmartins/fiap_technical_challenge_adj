package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.domain.exception.http409.IncompatibleOldPasswordException;
import br.com.fiap.tech.challenge_user.application.domain.exception.http500.InternalServerProblemException;
import br.com.fiap.tech.challenge_user.application.domain.model.Cliente;
import br.com.fiap.tech.challenge_user.application.domain.model.Usuario;
import br.com.fiap.tech.challenge_user.application.port.in.UsuarioSenhaInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioCreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioFindByIdOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.adapter.in.AbstractUsuarioSenhaController;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClienteSenhaService extends AbstractClienteSenhaService<ClienteEntity>
        implements UsuarioSenhaInputPort<ClienteEntity> {

    public ClienteSenhaService(
            UsuarioFindByIdOutputPort<ClienteEntity> findByIdOutputPort,
            UsuarioCreateOutputPort<ClienteEntity> createOutputPort) {
        super(findByIdOutputPort, createOutputPort);
    }

    @Override
    public void updatePassword(
            @NonNull final UUID usuarioId, @NonNull final String senhaAntiga, @NonNull final String senhaNova) {
        super.updatePassword(usuarioId, senhaAntiga, senhaNova);
    }
}

