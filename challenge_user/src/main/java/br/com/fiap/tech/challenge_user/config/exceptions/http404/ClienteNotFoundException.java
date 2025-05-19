package br.com.fiap.tech.challenge_user.config.exceptions.http404;

import java.io.Serial;
import java.util.UUID;

public final class ClienteNotFoundException extends ResourceNotFoundException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ClienteNotFoundException(final UUID id) {
        super("exception.resource.not.found.cliente", id);
    }
}

