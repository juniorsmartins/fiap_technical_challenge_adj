package br.com.fiap.tech.challenge_user.domain.entities;

import java.util.Objects;
import java.util.UUID;

public final class Endereco {

    private UUID enderecoId;

    private String cep;

    private String logradouro;

    private String numero;

    public Endereco() {
    }

    public UUID getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(UUID enderecoId) {
        this.enderecoId = enderecoId;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco = (Endereco) o;
        return Objects.equals(enderecoId, endereco.enderecoId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(enderecoId);
    }
}

