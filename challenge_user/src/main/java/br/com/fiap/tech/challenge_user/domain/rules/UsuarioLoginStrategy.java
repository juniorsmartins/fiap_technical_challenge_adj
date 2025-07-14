package br.com.fiap.tech.challenge_user.domain.rules;

import br.com.fiap.tech.challenge_user.application.interfaces.out.UsuarioFindByLoginOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http409.UsuarioNonUniqueLoginException;
import br.com.fiap.tech.challenge_user.domain.models.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UsuarioLoginStrategy<T extends Usuario> implements UsuarioRulesStrategy<T> {

    private final UsuarioFindByLoginOutputPort findByLoginOutputPort;

    @Override
    public void executar(T usuario) {

        var login = usuario.getLogin();
        var id = usuario.getUsuarioId();

        findByLoginOutputPort.findByLogin(login)
                .ifPresent(existe -> {
                    if (!existe.getUsuarioId().equals(id)) {
                        throw new UsuarioNonUniqueLoginException(login);
                    }
                });
    }
}

