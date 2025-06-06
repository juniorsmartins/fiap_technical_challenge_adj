package br.com.fiap.tech.challenge_user.application.domain.rule;

import br.com.fiap.tech.challenge_user.application.domain.exception.http409.UsuarioNonUniqueEmailException;
import br.com.fiap.tech.challenge_user.application.domain.model.Cliente;
import br.com.fiap.tech.challenge_user.application.domain.model.Usuario;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioFindByEmailOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UsuarioEmailStrategy implements UsuarioRulesStrategy {

    private final UsuarioFindByEmailOutputPort findByEmailOutputPort;

    @Override
    public <T extends Usuario> T executar(T usuario) {

        var email = usuario.getEmail();

        findByEmailOutputPort.findByEmail(email)
                .ifPresent(existe -> {
                    throw new UsuarioNonUniqueEmailException(email);
                });

        return usuario;
    }
}

