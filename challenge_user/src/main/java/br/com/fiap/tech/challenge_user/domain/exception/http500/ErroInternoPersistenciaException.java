package br.com.fiap.tech.challenge_user.domain.exception.http500;

import lombok.Getter;

import java.io.Serial;

@Getter
public abstract sealed class ErroInternoPersistenciaException extends RuntimeException
        permits RestauranteNonPersistenceException, UsuarioNonPersistenceException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String messageKey;

    private final String valor;

    public ErroInternoPersistenciaException(final String messageKey, final String valor) {
        super(messageKey);
        this.messageKey = messageKey;
        this.valor = valor;
    }
}

