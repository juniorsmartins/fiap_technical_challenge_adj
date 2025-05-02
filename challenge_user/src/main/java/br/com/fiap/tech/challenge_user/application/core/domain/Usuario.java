package br.com.fiap.tech.challenge_user.application.core.domain;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"usuarioId"})
public class Usuario {

    private UUID usuarioId;

    private String nome;

    private String email;

    private String login;

    private String senha;

    private TipoUsuarioEnum tipo;

    private Endereco endereco;
}

