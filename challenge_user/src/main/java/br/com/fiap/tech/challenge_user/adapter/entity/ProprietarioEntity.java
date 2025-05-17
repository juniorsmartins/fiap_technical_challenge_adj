package br.com.fiap.tech.challenge_user.adapter.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "proprietario")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public final class ProprietarioEntity extends UsuarioEntity {

    @Column(name = "descricao", nullable = true)
    private String descricao;

    public ProprietarioEntity() {}
}

