package br.com.fiap.tech.challenge_user.application.domain.exception.http409;

import java.io.Serial;

public final class UsuarioNonUniqueEmailException extends RegraDeNegocioVioladaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UsuarioNonUniqueEmailException(final String email) {
        super("exception.conflict.non-unique-email", email);
    }
}

