package br.com.fiap.tech.challenge_user.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public final class Cliente extends Usuario {

    private String numeroCartaoFidelidade;

    public Cliente(
            UUID usuarioId, String nome, String email, String login, String senha,
            Endereco endereco,
            String numeroCartaoFidelidade) {
        super(usuarioId, nome, email, login, senha, endereco);
        this.numeroCartaoFidelidade = numeroCartaoFidelidade;
    }

    public Cliente(
            String nome, String email, String login, String senha,
            br.com.fiap.tech.challenge_user.application.domain.model.Endereco endereco,
            String numeroCartaoFidelidade) {
        super(nome, email, login, senha, endereco);
        this.numeroCartaoFidelidade = numeroCartaoFidelidade;
    }
}

