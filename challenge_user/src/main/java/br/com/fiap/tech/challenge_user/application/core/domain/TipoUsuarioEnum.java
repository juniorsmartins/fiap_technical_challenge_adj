package br.com.fiap.tech.challenge_user.application.core.domain;

public enum TipoUsuarioEnum {

    CLIENTE("CLIENTE"),
    PROPRIETARIO("PROPRIETARIO");

    private final String value;

    TipoUsuarioEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

