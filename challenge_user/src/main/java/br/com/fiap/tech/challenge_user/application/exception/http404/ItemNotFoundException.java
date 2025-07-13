package br.com.fiap.tech.challenge_user.application.exception.http404;

import java.io.Serial;
import java.util.UUID;

public final class ItemNotFoundException extends ResourceNotFoundException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ItemNotFoundException(final UUID id) {
        super("exception.resource.not.found.item", id);
    }
}

