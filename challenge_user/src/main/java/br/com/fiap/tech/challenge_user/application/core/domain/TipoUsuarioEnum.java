package br.com.fiap.tech.challenge_user.application.core.domain;

import lombok.Getter;

@Getter
public enum TipoUsuarioEnum {

    CLIENTE("CLIENTE"),
    PROPRIETARIO("PROPRIETARIO");

    private final String value;

    TipoUsuarioEnum(String value) {
        this.value = value;
    }
}

