package br.com.fiap.tech.challenge_user.domain.model;

import java.util.Objects;
import java.util.UUID;

public abstract class Usuario {

    private UUID usuarioId;

    private String nome;

    private String email;

    private String login;

    private String senha;

    private Endereco endereco;

    protected Usuario() {
    }

    public Usuario(String nome,
                   String email,
                   String login,
                   String senha,
                   Endereco endereco) {
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
                   Endereco endereco) {
        this(nome, email, login, senha, endereco);
        this.usuarioId = usuarioId;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(usuarioId, usuario.usuarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(usuarioId);
    }
}

