package br.com.fiap.tech.challenge_user.application.exception.http500;

import java.io.Serial;

public final class ItemNonPersistenceException extends ErroInternoPersistenciaException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ItemNonPersistenceException(final String valor) {
        super("exception.internal.server.error.persistence", valor);
    }
}

