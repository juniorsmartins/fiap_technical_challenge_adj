package br.com.fiap.tech.challenge_user.domain.models;

import br.com.fiap.tech.challenge_user.application.exception.http409.OpeningTimeLaterClosingTimeException;
import br.com.fiap.tech.challenge_user.domain.models.enums.TipoCozinhaEnum;

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

        this.checkHoraFuncionamento();
    }

    public void setHoraAbertura(LocalTime horaAbertura) {
        this.horaAbertura = horaAbertura;
        this.checkHoraFuncionamento();
    }

    public void setHoraFechamento(LocalTime horarioFim) {

        this.horaFechamento = horarioFim;
        this.checkHoraFuncionamento();
    }

    private void checkHoraFuncionamento() {

        if (this.horaFechamento != null && this.horaAbertura != null && this.horaFechamento.isBefore(this.horaAbertura)) {
            throw new OpeningTimeLaterClosingTimeException(this.horaFechamento.toString());
        }
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
    }

    public TipoCozinhaEnum getTipoCozinhaEnum() {
        return tipoCozinhaEnum;
    }

    public void setTipoCozinhaEnum(TipoCozinhaEnum tipoCozinhaEnum) {
        this.tipoCozinhaEnum = tipoCozinhaEnum;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
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
}

