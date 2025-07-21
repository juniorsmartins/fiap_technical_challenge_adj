package br.com.fiap.tech.challenge_user.application.exception.http409;

import java.io.Serial;

public final class AtributoInvalidoException extends RegraDeNegocioVioladaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public AtributoInvalidoException(final String nomeAtributo) {
        super("exception.conflict.invalid-attribute", nomeAtributo);
    }
}

