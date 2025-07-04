package br.com.fiap.tech.challenge_user.domain.model;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"enderecoId"})
public final class Endereco {

    private UUID enderecoId;

    private String cep;

    private String logradouro;

    private String numero;
}

