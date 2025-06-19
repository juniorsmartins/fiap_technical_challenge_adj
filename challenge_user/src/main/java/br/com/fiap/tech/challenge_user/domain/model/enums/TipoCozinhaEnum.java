package br.com.fiap.tech.challenge_user.domain.model.enums;

import lombok.Getter;

@Getter
public enum TipoCozinhaEnum {

    BRASILEIRA("BRASILEIRA"),
    ITALIANA("ITALIANA"),
    JAPONESA("JAPONESA"),
    MEXICANA("MEXICANA"),
    ARABE("ARABE"),
    CHINESA("CHINESA"),
    TURCA("TURCA"),
    CHILENA("CHILENA"),
    FAST_FOOD("FAST_FOOD"),
    CARNIVORA("CARNIVORA"),
    VEGANA("VEGANA"),
    VEGETARIANA("VEGETARIANA");

    private final String value;

    TipoCozinhaEnum(String value) {
        this.value = value;
    }
}

