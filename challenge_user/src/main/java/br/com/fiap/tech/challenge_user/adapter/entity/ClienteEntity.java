package br.com.fiap.tech.challenge_user.adapter.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public final class ClienteEntity extends UsuarioEntity {

    @Column(name = "numero_cartao_fidelidade", nullable = true)
    private String numeroCartaoFidelidade;

    public ClienteEntity() {}

    public ClienteEntity(
            String nome, String email, String login, String senha,
            EnderecoEntity enderecoEntity, String numeroCartaoFidelidade,
            Date dataHoraCriacao, Date dataHoraEdicao
    ) {
        super(nome, email, login, senha, enderecoEntity, dataHoraCriacao, dataHoraEdicao);
        this.numeroCartaoFidelidade = numeroCartaoFidelidade;
    }

    public ClienteEntity(
            UUID usuarioId, String nome, String email, String login, String senha,
            EnderecoEntity enderecoEntity, String numeroCartaoFidelidade,
            Date dataHoraCriacao, Date dataHoraEdicao
    ) {
        super(usuarioId, nome, email, login, senha, enderecoEntity, dataHoraCriacao, dataHoraEdicao);
        this.numeroCartaoFidelidade = numeroCartaoFidelidade;
    }
}

