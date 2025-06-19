package br.com.fiap.tech.challenge_user.domain.model;

import br.com.fiap.tech.challenge_user.domain.model.enums.TipoCozinhaEnum;
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

    private TipoCozinhaEnum tipoCozinhaEnum;

    private Endereco endereco;

    private Proprietario proprietario;
}

