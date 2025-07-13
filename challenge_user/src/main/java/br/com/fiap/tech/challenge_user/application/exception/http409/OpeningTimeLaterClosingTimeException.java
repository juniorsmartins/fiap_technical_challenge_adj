package br.com.fiap.tech.challenge_user.application.exception.http409;

public final class OpeningTimeLaterClosingTimeException extends RegraDeNegocioVioladaException {

    public OpeningTimeLaterClosingTimeException(String horaFechamento) {
        super("exception.conflict.opening-time-later-closing-time", horaFechamento);
    }
}

