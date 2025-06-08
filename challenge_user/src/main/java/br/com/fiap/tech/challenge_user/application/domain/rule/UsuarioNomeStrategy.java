package br.com.fiap.tech.challenge_user.application.domain.rule;

import br.com.fiap.tech.challenge_user.application.domain.exception.http409.UsuarioNonUniqueNomeException;
import br.com.fiap.tech.challenge_user.application.domain.model.Usuario;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioFindByNomeOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UsuarioNomeStrategy<T extends Usuario> implements UsuarioRulesStrategy<T> {

    private final UsuarioFindByNomeOutputPort findByNomeOutputPort;

    @Override
    public T executar(T usuario) {

        var nome = usuario.getNome();
        var id = usuario.getUsuarioId();

        findByNomeOutputPort.findByNome(nome)
                .ifPresent(existe -> {
                    if (!existe.getUsuarioId().equals(id)) {
                        throw new UsuarioNonUniqueNomeException(nome);
                    }
                });

        return usuario;
    }
}

