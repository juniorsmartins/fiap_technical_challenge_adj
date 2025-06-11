package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.domain.exception.http404.UsuarioNotFoundException;
import br.com.fiap.tech.challenge_user.domain.exception.http409.IncompatibleOldPasswordException;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioCreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioFindByIdOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.entity.UsuarioEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class AbstractClienteSenhaService<E extends UsuarioEntity> {

    private final UsuarioFindByIdOutputPort<E> findByIdOutputPort;

    private final UsuarioCreateOutputPort<E> createOutputPort;

    public void updatePassword(
            @NonNull final UUID usuarioId, @NonNull final String senhaAntiga, @NonNull final String senhaNova) {

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

