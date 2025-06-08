package br.com.fiap.tech.challenge_user.application.domain.rule;

import br.com.fiap.tech.challenge_user.application.domain.exception.http409.UsuarioNonUniqueEmailException;
import br.com.fiap.tech.challenge_user.application.domain.model.Usuario;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioFindByLoginOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UsuarioLoginStrategy<T extends Usuario> implements UsuarioRulesStrategy<T> {

    private final UsuarioFindByLoginOutputPort findByLoginOutputPort;

    @Override
    public T executar(T usuario) {

        var login = usuario.getLogin();

        findByLoginOutputPort.findByLogin(login)
                .ifPresent(existe -> {
                    throw new UsuarioNonUniqueEmailException(login);
                });

        return usuario;
    }
}

