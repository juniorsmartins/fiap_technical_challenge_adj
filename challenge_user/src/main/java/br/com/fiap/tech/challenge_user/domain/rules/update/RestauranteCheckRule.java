package br.com.fiap.tech.challenge_user.domain.rules.update;

import br.com.fiap.tech.challenge_user.domain.models.Restaurante;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ProprietarioEntity;

public interface RestauranteCheckRule {

    ProprietarioEntity checkProprietario(Restaurante restaurante);
}

