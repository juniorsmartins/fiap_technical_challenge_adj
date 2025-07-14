package br.com.fiap.tech.challenge_user.domain.rule;

import br.com.fiap.tech.challenge_user.application.interfaces.out.UsuarioFindByNomeOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http409.UsuarioNonUniqueNomeException;
import br.com.fiap.tech.challenge_user.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UsuarioNomeStrategy<T extends Usuario> implements UsuarioRulesStrategy<T> {

    private final UsuarioFindByNomeOutputPort findByNomeOutputPort;

    @Override
    public void executar(T usuario) {

        var nome = usuario.getNome();
        var id = usuario.getUsuarioId();

        findByNomeOutputPort.findByNome(nome)
                .ifPresent(existe -> {
                    if (!existe.getUsuarioId().equals(id)) {
                        throw new UsuarioNonUniqueNomeException(nome);
                    }
                });
    }
}

