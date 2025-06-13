package br.com.fiap.tech.challenge_user.domain.exception.http404;

import java.io.Serial;
import java.util.UUID;

public final class RestauranteNotFoundException extends ResourceNotFoundException {

    @Serial
    private static final long serialVersionUID = 1L;

    public RestauranteNotFoundException(final UUID id) {
        super("exception.resource.not.found.restaurante", id);
    }
}

