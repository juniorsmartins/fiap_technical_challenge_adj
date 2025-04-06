package br.com.fiap.tech.challenge_user.config.exceptions.http404;

import java.io.Serial;
import java.util.UUID;

public final class UsuarioNotFoundException extends ResourceNotFoundException{

    @Serial
    private static final long serialVersionUID = 1L;

    public UsuarioNotFoundException(final UUID id) {
        super("exception.resource.not.found.usuario", id);
    }
}

