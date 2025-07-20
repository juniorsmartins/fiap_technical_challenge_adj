package br.com.fiap.tech.challenge_user.domain.rules;

import br.com.fiap.tech.challenge_user.application.interfaces.out.UsuarioFindByEmailOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http409.UsuarioNonUniqueEmailException;
import br.com.fiap.tech.challenge_user.domain.entities.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UsuarioEmailStrategy<T extends Usuario> implements UsuarioRulesStrategy<T> {

    private final UsuarioFindByEmailOutputPort findByEmailOutputPort;

    @Override
    public void executar(T usuario) {

        var email = usuario.getEmail();
        var id = usuario.getUsuarioId();

        findByEmailOutputPort.findByEmail(email)
                .ifPresent(existe -> {
                    if (!existe.getUsuarioId().equals(id)) {
                        throw new UsuarioNonUniqueEmailException(email);
                    }
                });
    }
}

