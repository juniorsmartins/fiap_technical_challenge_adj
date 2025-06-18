package br.com.fiap.tech.challenge_user.domain.rule.update;

import br.com.fiap.tech.challenge_user.domain.model.Restaurante;

public interface RestauranteCheckRule {

    void checkProprietario(Restaurante restaurante);
}

