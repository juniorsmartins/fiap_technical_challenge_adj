package br.com.fiap.tech.challenge_user.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public final class Item implements Serializable {

    private UUID itemId;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private boolean entrega;

    private String foto;

    public Item(String nome, String descricao, BigDecimal preco, boolean entrega, String foto) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.entrega = entrega;
        this.foto = foto;
    }

    public Item(UUID itemId, String nome, String descricao, BigDecimal preco, boolean entrega, String foto) {
        this(nome, descricao, preco, entrega, foto);
        this.itemId = itemId;
    }
}

