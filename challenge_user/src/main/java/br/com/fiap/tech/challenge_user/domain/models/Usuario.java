package br.com.fiap.tech.challenge_user.domain.models;

import br.com.fiap.tech.challenge_user.application.exception.http409.AtributoInvalidoException;
import br.com.fiap.tech.challenge_user.application.exception.http409.AtributoObrigatorioException;
import br.com.fiap.tech.challenge_user.application.exception.http409.AtributoTamanhoLimitadoException;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;

import java.util.Objects;
import java.util.UUID;

import static br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsValidation.MAX_CARACTER_LOGIN;
import static br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsValidation.MAX_CARACTER_NOME;

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
        validarAtributoNome(nome);
        validarAtributoEmail(email);
        validarAtributoLogin(login);
    }

    public Usuario(UUID usuarioId,
                   String nome,
                   String email,
                   String login,
                   String senha,
                   Endereco endereco) {
        this(nome, email, login, senha, endereco);
        this.usuarioId = usuarioId;
        validarAtributoNome(nome);
        validarAtributoEmail(email);
        validarAtributoLogin(login);
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
        validarAtributoNome(nome);
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validarAtributoEmail(email);
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
        validarAtributoLogin(login);
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

    private void validarAtributoNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new AtributoObrigatorioException("nome");
        }
        if (nome.length() > MAX_CARACTER_NOME) {
            throw new AtributoTamanhoLimitadoException(String.valueOf(MAX_CARACTER_NOME));
        }
    }

    private void validarAtributoEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new AtributoObrigatorioException("email");
        }
        EmailValidator emailValidator = new EmailValidator();
        emailValidator.initialize(null);
        if (!emailValidator.isValid(email.trim(), null)) {
            throw new AtributoInvalidoException("email");
        }
    }

    private void validarAtributoLogin(String login) {
        if (login == null || login.isBlank()) {
            throw new AtributoObrigatorioException("login");
        }
        if (nome.length() > MAX_CARACTER_LOGIN) {
            throw new AtributoTamanhoLimitadoException(String.valueOf(MAX_CARACTER_LOGIN));
        }
    }
}

