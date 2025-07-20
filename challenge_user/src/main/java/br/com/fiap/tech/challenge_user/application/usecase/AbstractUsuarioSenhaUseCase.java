package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.UsuarioNotFoundException;
import br.com.fiap.tech.challenge_user.application.exception.http409.IncompatibleOldPasswordException;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.UsuarioDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class AbstractUsuarioSenhaUseCase<E extends UsuarioDao> {

    private final FindByIdOutputPort<E> findByIdOutputPort;

    private final CreateOutputPort<E> createOutputPort;

    public void updatePassword(
            final UUID usuarioId, final String senhaAntiga, final String senhaNova) {

        findByIdOutputPort.findById(usuarioId)
                .ifPresentOrElse(user -> {
                    this.checkOldPassword(senhaAntiga, user);
                    this.replacePassword(senhaNova, user);
                    this.createOutputPort.save(user);
                }, () -> {
                    log.error("AbstractClienteSenhaService - Usuário não encontrado por id: {}.", usuarioId);
                    throw new UsuarioNotFoundException(usuarioId);
                });
    }

    private E checkOldPassword(String senhaAntiga, E usuario) {
        if (!usuario.getSenha().equals(senhaAntiga)) {
            throw new IncompatibleOldPasswordException(senhaAntiga);
        }

        return usuario;
    }

    private E replacePassword(String senhaNova, E usuario) {

        usuario.setSenha(senhaNova);
        return usuario;
    }
}

