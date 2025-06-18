package br.com.fiap.tech.challenge_user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public final class Restaurante {

    private UUID restauranteId;

    private String nome;

    private Endereco endereco;

    private Proprietario proprietario;
}

