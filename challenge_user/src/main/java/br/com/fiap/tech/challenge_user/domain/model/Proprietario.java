package br.com.fiap.tech.challenge_user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public final class Proprietario extends Usuario {

    private String descricao;

    public Proprietario(
            UUID usuarioId, String nome, String email, String login, String senha,
            Endereco endereco,
            String descricao) {
        super(usuarioId, nome, email, login, senha, endereco);
        this.descricao = descricao;
    }

    public Proprietario(
            String nome, String email, String login, String senha,
            Endereco endereco,
            String descricao) {
        super(nome, email, login, senha, endereco);
        this.descricao = descricao;
    }
}

