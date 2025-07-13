package br.com.fiap.tech.challenge_user.domain.rule;

import br.com.fiap.tech.challenge_user.application.port.out.UsuarioFindByEmailOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http409.UsuarioNonUniqueEmailException;
import br.com.fiap.tech.challenge_user.domain.model.Usuario;
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

