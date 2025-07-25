package br.com.fiap.tech.challenge_user.infrastructure.drivers.daos;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import static br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsValidation.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = {"usuarioId"}, callSuper = false)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class UsuarioDao extends AbstractAuditingDao implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "usuario_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID usuarioId;

    @Column(name = "nome", length = MAX_CARACTER_NOME, nullable = false)
    private String nome;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "login", length = MAX_CARACTER_LOGIN, nullable = false)
    private String login;

    @Column(name = "senha", length = MAX_CARACTER_SENHA, nullable = false)
    private String senha;

    @OneToOne(targetEntity = EnderecoDao.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true, orphanRemoval = true)
    @JoinColumn(name = "endereco_id", unique = true, nullable = true)
    private EnderecoDao endereco;

    protected UsuarioDao() {
    }

    public UsuarioDao(
            String nome,
            String email,
            String login,
            String senha,
            EnderecoDao endereco) {
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.endereco = endereco;
    }

    public UsuarioDao(
            String nome,
            String email,
            String login,
            String senha,
            EnderecoDao endereco,
            Date dataHoraCriacao,
            Date dataHoraEdicao) {
        this(nome, email, login, senha, endereco);
        this.setDataHoraCriacao(dataHoraCriacao);
        this.setDataHoraEdicao(dataHoraEdicao);
    }

    public UsuarioDao(
            UUID usuarioId,
            String nome,
            String email,
            String login,
            String senha,
            EnderecoDao endereco,
            Date dataHoraCriacao,
            Date dataHoraEdicao) {
        this(nome, email, login, senha, endereco, dataHoraCriacao, dataHoraEdicao);
        this.usuarioId = usuarioId;
    }
}

