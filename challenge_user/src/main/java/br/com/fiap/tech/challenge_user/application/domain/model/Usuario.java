package br.com.fiap.tech.challenge_user.application.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(of = {"usuarioId"})
public abstract class Usuario {

    private UUID usuarioId;

    private String nome;

    private String email;

    private String login;

    private String senha;

    private br.com.fiap.tech.challenge_user.application.domain.model.Endereco endereco;

    protected Usuario() {
    }

    public Usuario(String nome,
                   String email,
                   String login,
                   String senha,
                   br.com.fiap.tech.challenge_user.application.domain.model.Endereco endereco) {
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.endereco = endereco;
    }

    public Usuario(UUID usuarioId,
                   String nome,
                   String email,
                   String login,
                   String senha,
                   br.com.fiap.tech.challenge_user.application.domain.model.Endereco endereco) {
        this(nome, email, login, senha, endereco);
        this.usuarioId = usuarioId;
    }
}

