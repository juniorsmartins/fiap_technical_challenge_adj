package br.com.fiap.tech.challenge_user.domain.rule.update;

import br.com.fiap.tech.challenge_user.domain.model.Restaurante;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ProprietarioEntity;

public interface RestauranteCheckRule {

    ProprietarioEntity checkProprietario(Restaurante restaurante);
}

