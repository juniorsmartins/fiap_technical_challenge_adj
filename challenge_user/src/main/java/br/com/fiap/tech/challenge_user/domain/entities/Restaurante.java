package br.com.fiap.tech.challenge_user.domain.entities;

import br.com.fiap.tech.challenge_user.application.exception.http409.AtributoObrigatorioException;
import br.com.fiap.tech.challenge_user.application.exception.http409.OpeningTimeLaterClosingTimeException;
import br.com.fiap.tech.challenge_user.domain.entities.enums.TipoCozinhaEnum;

import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

public final class Restaurante {

    private UUID restauranteId;

    private String nome;

    private TipoCozinhaEnum tipoCozinhaEnum;

    private LocalTime horaAbertura;

    private LocalTime horaFechamento;

    private Endereco endereco;

    private Proprietario proprietario;

    public Restaurante() {
    }

    public Restaurante(
            UUID restauranteId, String nome, TipoCozinhaEnum tipoCozinhaEnum,
            LocalTime horaAbertura, LocalTime horaFechamento, Endereco endereco, Proprietario proprietario
    ) {
        this.restauranteId = restauranteId;
        this.nome = nome;
        this.tipoCozinhaEnum = tipoCozinhaEnum;
        this.horaAbertura = horaAbertura;
        this.horaFechamento = horaFechamento;
        this.endereco = endereco;
        this.proprietario = proprietario;

        validarAtributoNome(nome);
        validarAtributoTipoCozinhaEnum(tipoCozinhaEnum);
        validarAtributosHora("horaAbertura", horaAbertura);
        validarAtributosHora("horaFechamento", horaFechamento);
        checkHoraFuncionamento(horaAbertura, horaFechamento);
        validarAtributosEndereco(endereco);
        validarAtributosProprietario(proprietario);
    }

    public void setHoraAbertura(LocalTime horaAbertura) {
        this.horaAbertura = horaAbertura;
        validarAtributosHora("horaAbertura", horaAbertura);
        this.checkHoraFuncionamento(horaAbertura, this.horaFechamento);
    }

    public void setHoraFechamento(LocalTime horaFim) {
        this.horaFechamento = horaFim;
        validarAtributosHora("horaFechamento", horaFechamento);
        this.checkHoraFuncionamento(this.horaAbertura, horaFim);
    }

    public LocalTime getHoraAbertura() {
        return horaAbertura;
    }

    public LocalTime getHoraFechamento() {
        return horaFechamento;
    }

    public UUID getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(UUID restauranteId) {
        this.restauranteId = restauranteId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        validarAtributoNome(nome);
    }

    public TipoCozinhaEnum getTipoCozinhaEnum() {
        return tipoCozinhaEnum;
    }

    public void setTipoCozinhaEnum(TipoCozinhaEnum tipoCozinhaEnum) {
        this.tipoCozinhaEnum = tipoCozinhaEnum;
        validarAtributoTipoCozinhaEnum(tipoCozinhaEnum);
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
        validarAtributosEndereco(endereco);
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
        validarAtributosProprietario(proprietario);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Restaurante that = (Restaurante) o;
        return Objects.equals(restauranteId, that.restauranteId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(restauranteId);
    }

    private void validarAtributoNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new AtributoObrigatorioException("nome");
        }
    }

    private void validarAtributoTipoCozinhaEnum(TipoCozinhaEnum tipoCozinhaEnum) {
        if (tipoCozinhaEnum == null) {
            throw new AtributoObrigatorioException("tipoCozinhaEnum");
        }
    }

    private void checkHoraFuncionamento(LocalTime horaAbertura, LocalTime horaFechamento) {
        if (horaAbertura != null && horaFechamento != null && horaFechamento.isBefore(horaAbertura)) {
            throw new OpeningTimeLaterClosingTimeException(horaAbertura + " e " + horaFechamento);
        }
    }

    private void validarAtributosHora(String nomeAtributo, LocalTime hora) {
        if (hora == null) {
            throw new AtributoObrigatorioException(nomeAtributo);
        }
    }

    private void validarAtributosEndereco(Endereco endereco) {
        if (endereco == null) {
            throw new AtributoObrigatorioException("endereco");
        }
    }

    private void validarAtributosProprietario(Proprietario proprietario) {
        if (proprietario == null) {
            throw new AtributoObrigatorioException("proprietario");
        }
    }
}

