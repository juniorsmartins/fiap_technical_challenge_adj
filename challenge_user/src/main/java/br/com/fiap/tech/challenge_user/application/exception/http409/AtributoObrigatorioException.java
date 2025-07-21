package br.com.fiap.tech.challenge_user.application.exception.http409;

import java.io.Serial;

public final class AtributoObrigatorioException extends RegraDeNegocioVioladaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public AtributoObrigatorioException(final String nomeAtributo) {
        super("exception.conflict.mandatory-attribute", nomeAtributo);
    }
}

