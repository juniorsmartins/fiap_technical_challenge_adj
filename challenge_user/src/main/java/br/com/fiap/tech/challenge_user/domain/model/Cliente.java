package br.com.fiap.tech.challenge_user.domain.model;

import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public final class Cliente extends Usuario {

    private String numeroCartaoFidelidade;

    public Cliente() {
    }

    public Cliente(
            UUID usuarioId, String nome, String email, String login, String senha,
            Endereco endereco,
            String numeroCartaoFidelidade) {
        super(usuarioId, nome, email, login, senha, endereco);
        this.numeroCartaoFidelidade = numeroCartaoFidelidade;
    }

    public Cliente(
            String nome, String email, String login, String senha,
            Endereco endereco,
            String numeroCartaoFidelidade) {
        super(nome, email, login, senha, endereco);
        this.numeroCartaoFidelidade = numeroCartaoFidelidade;
    }

    public String getNumeroCartaoFidelidade() {
        return numeroCartaoFidelidade;
    }
}

