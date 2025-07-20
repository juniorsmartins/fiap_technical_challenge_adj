package br.com.fiap.tech.challenge_user.domain.models;

import br.com.fiap.tech.challenge_user.application.exception.http409.AtributoObrigatorioException;

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
        validarAtributoNome(nome);
        validarAtributoPreco(preco);
        validarAtributoFoto(foto);
        validarAtributoDescricao(descricao);
    }

    public Item(UUID itemId, String nome, String descricao, BigDecimal preco, boolean entrega, String foto) {
        this(nome, descricao, preco, entrega, foto);
        this.itemId = itemId;
        validarAtributoNome(nome);
        validarAtributoPreco(preco);
        validarAtributoFoto(foto);
        validarAtributoDescricao(descricao);
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
        validarAtributoNome(nome);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
        validarAtributoDescricao(descricao);
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
        validarAtributoPreco(preco);
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
        validarAtributoFoto(foto);
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

    private void validarAtributoNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new AtributoObrigatorioException("nome");
        }
    }

    private void validarAtributoDescricao(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            throw new AtributoObrigatorioException("descrição");
        }
    }

    private void validarAtributoPreco(BigDecimal preco) {
        if (preco == null) {
            throw new AtributoObrigatorioException("preço");
        }
    }

    private void validarAtributoFoto(String foto) {
        if (foto == null || foto.isBlank()) {
            throw new AtributoObrigatorioException("foto");
        }
    }
}

