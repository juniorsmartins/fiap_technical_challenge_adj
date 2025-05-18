package br.com.fiap.tech.challenge_user.adapter.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "proprietarios")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public final class ProprietarioEntity extends UsuarioEntity {

    @Column(name = "descricao", nullable = true)
    private String descricao;

    public ProprietarioEntity(
            String nome, String email, String login, String senha,
            EnderecoEntity enderecoEntity, String descricao,
            Date dataHoraCriacao, Date dataHoraEdicao
    ) {
        super(nome, email, login, senha, enderecoEntity, dataHoraCriacao, dataHoraEdicao);
        this.descricao = descricao;
    }

    public ProprietarioEntity(
            UUID usuarioId, String nome, String email, String login, String senha,
            EnderecoEntity enderecoEntity, String descricao,
            Date dataHoraCriacao, Date dataHoraEdicao
    ) {
        super(usuarioId, nome, email, login, senha, enderecoEntity, dataHoraCriacao, dataHoraEdicao);
        this.descricao = descricao;
    }
}

