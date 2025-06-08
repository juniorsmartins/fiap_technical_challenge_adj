package br.com.fiap.tech.challenge_user.application.domain.exception.http409;

import lombok.Getter;

import java.io.Serial;

@Getter
public abstract sealed class RegraDeNegocioVioladaException extends RuntimeException
        permits UsuarioNonUniqueEmailException, UsuarioNonUniqueLoginException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String messageKey;

    private final String valor;

    public RegraDeNegocioVioladaException(final String messageKey, final String valor) {
        super(messageKey);
        this.messageKey = messageKey;
        this.valor = valor;
    }
}
