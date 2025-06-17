package br.com.fiap.tech.challenge_user.domain.exception.http404;

import java.io.Serial;
import java.util.UUID;

public final class ProprietarioNotFoundException extends ResourceNotFoundException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ProprietarioNotFoundException(final UUID id) {
        super("exception.resource.not.found.proprietario", id);
    }
}

