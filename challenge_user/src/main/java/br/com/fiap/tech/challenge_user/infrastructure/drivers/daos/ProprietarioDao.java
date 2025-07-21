package br.com.fiap.tech.challenge_user.infrastructure.drivers.daos;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "proprietarios")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public final class ProprietarioDao extends UsuarioDao {

    @Column(name = "descricao", nullable = true)
    private String descricao;

    @OneToMany(mappedBy = "proprietario", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = false)
    private List<RestauranteDao> restaurantes = new ArrayList<>();

    public ProprietarioDao(
            String nome, String email, String login, String senha,
            EnderecoDao enderecoDao, String descricao,
            Date dataHoraCriacao, Date dataHoraEdicao
    ) {
        super(nome, email, login, senha, enderecoDao, dataHoraCriacao, dataHoraEdicao);
        this.descricao = descricao;
    }

    public ProprietarioDao(
            UUID usuarioId, String nome, String email, String login, String senha,
            EnderecoDao enderecoDao, String descricao,
            Date dataHoraCriacao, Date dataHoraEdicao
    ) {
        super(usuarioId, nome, email, login, senha, enderecoDao, dataHoraCriacao, dataHoraEdicao);
        this.descricao = descricao;
    }
}

