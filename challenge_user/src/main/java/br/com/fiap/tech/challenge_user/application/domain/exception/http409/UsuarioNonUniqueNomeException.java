package br.com.fiap.tech.challenge_user.application.domain.exception.http409;

import java.io.Serial;

public final class UsuarioNonUniqueNomeException extends RegraDeNegocioVioladaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UsuarioNonUniqueNomeException(final String nome) {
        super("exception.conflict.non-unique-nome", nome);
    }
}

