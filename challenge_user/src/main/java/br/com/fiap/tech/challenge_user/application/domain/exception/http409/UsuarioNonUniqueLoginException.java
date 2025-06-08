package br.com.fiap.tech.challenge_user.application.domain.exception.http409;

import java.io.Serial;

public final class UsuarioNonUniqueLoginException extends RegraDeNegocioVioladaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UsuarioNonUniqueLoginException(final String login) {
        super("exception.conflict.non-unique-login", login);
    }
}

