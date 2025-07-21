package br.com.fiap.tech.challenge_user.application.exception.http409;

import java.io.Serial;

public final class AtributoTamanhoLimitadoException extends RegraDeNegocioVioladaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public AtributoTamanhoLimitadoException(final String limiteMaximo) {
        super("exception.conflict.attribute-limited-size", limiteMaximo);
    }
}

