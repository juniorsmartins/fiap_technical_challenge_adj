package br.com.fiap.tech.challenge_user.domain.model;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"enderecoId"})
public class Endereco {

    private UUID enderecoId;

    private String cep;

    private String logradouro;

    private String numero;
}

