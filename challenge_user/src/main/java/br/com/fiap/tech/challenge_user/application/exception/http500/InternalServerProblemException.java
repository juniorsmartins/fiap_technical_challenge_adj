package br.com.fiap.tech.challenge_user.application.exception.http500;

import java.io.Serial;

public final class InternalServerProblemException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InternalServerProblemException() {
        super("exception.internal.server.error");
    }
}

