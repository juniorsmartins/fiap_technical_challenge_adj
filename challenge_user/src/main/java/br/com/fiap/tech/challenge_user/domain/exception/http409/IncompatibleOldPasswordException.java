package br.com.fiap.tech.challenge_user.domain.exception.http409;

import java.io.Serial;

public final class IncompatibleOldPasswordException extends RegraDeNegocioVioladaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public IncompatibleOldPasswordException(String senhaAntiga) {
        super("exception.conflict.incompatible-old-password", senhaAntiga);
    }
}

