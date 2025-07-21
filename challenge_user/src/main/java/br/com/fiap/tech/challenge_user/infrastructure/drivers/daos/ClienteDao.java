package br.com.fiap.tech.challenge_user.infrastructure.drivers.daos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "clientes")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public final class ClienteDao extends UsuarioDao {

    @Column(name = "numero_cartao_fidelidade", nullable = true)
    private String numeroCartaoFidelidade;

    public ClienteDao(
            String nome, String email, String login, String senha,
            EnderecoDao enderecoDao, String numeroCartaoFidelidade,
            Date dataHoraCriacao, Date dataHoraEdicao
    ) {
        super(nome, email, login, senha, enderecoDao, dataHoraCriacao, dataHoraEdicao);
        this.numeroCartaoFidelidade = numeroCartaoFidelidade;
    }

    public ClienteDao(
            UUID usuarioId, String nome, String email, String login, String senha,
            EnderecoDao enderecoDao, String numeroCartaoFidelidade,
            Date dataHoraCriacao, Date dataHoraEdicao
    ) {
        super(usuarioId, nome, email, login, senha, enderecoDao, dataHoraCriacao, dataHoraEdicao);
        this.numeroCartaoFidelidade = numeroCartaoFidelidade;
    }
}

