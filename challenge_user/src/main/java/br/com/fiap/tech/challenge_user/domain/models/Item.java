package br.com.fiap.tech.challenge_user.domain.models;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public final class Item {

    private UUID itemId;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private boolean entrega;

    private String foto;

    public Item() {
    }

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

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public boolean isEntrega() {
        return entrega;
    }

    public void setEntrega(boolean entrega) {
        this.entrega = entrega;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(itemId, item.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(itemId);
    }
}

