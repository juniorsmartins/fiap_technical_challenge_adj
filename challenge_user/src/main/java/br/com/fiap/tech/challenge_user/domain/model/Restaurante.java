package br.com.fiap.tech.challenge_user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public final class Restaurante {

    private UUID restauranteId;

    private String nome;
}

