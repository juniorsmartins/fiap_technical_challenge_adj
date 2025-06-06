package br.com.fiap.tech.challenge_user.application.domain.exception.http409;

import lombok.Getter;

import java.io.Serial;

@Getter
public abstract sealed class RegraDeNegocioVioladaException extends RuntimeException permits UsuarioNonUniqueEmailException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String messageKey;

    private final String email;

    public RegraDeNegocioVioladaException(final String messageKey, final String email) {
        super(messageKey);
        this.messageKey = messageKey;
        this.email = email;
    }
}
