package br.com.fiap.tech.challenge_user.domain.model;

import br.com.fiap.tech.challenge_user.domain.exception.http409.OpeningTimeLaterClosingTimeException;
import br.com.fiap.tech.challenge_user.domain.model.enums.TipoCozinhaEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public final class Restaurante {

    private UUID restauranteId;

    private String nome;

    private TipoCozinhaEnum tipoCozinhaEnum;

    private LocalTime horaAbertura;

    private LocalTime horaFechamento;

    private Endereco endereco;

    private Proprietario proprietario;

    public Restaurante(
            UUID restauranteId, String nome, TipoCozinhaEnum tipoCozinhaEnum,
            LocalTime horaAbertura, LocalTime horaFechamento, Endereco endereco, Proprietario proprietario
    ) {
        this.restauranteId = restauranteId;
        this.nome = nome;
        this.tipoCozinhaEnum = tipoCozinhaEnum;
        this.horaAbertura = horaAbertura;
        this.checkHoraFechamento(horaFechamento);
        this.horaFechamento = horaFechamento;
        this.endereco = endereco;
        this.proprietario = proprietario;
    }

    public void setHoraFechamento(LocalTime horarioFim) {

        this.checkHoraFechamento(horarioFim);
        this.horaFechamento = horarioFim;
    }

    private void checkHoraFechamento(LocalTime horarioFim) {
        if (horarioFim != null && this.horaAbertura != null && horarioFim.isBefore(this.horaAbertura)) {
            throw new OpeningTimeLaterClosingTimeException(horarioFim.toString());
        }
    }
}

