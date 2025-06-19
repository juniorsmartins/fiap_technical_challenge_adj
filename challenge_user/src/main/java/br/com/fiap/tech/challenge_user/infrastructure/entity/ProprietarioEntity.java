package br.com.fiap.tech.challenge_user.infrastructure.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToOne(mappedBy = "proprietario", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, optional = true, orphanRemoval = false)
    private RestauranteEntity restaurante;

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

