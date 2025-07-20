package br.com.fiap.tech.challenge_user.domain.rules.update;

import br.com.fiap.tech.challenge_user.domain.models.Restaurante;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;

public interface RestauranteCheckRule {

    ProprietarioDao checkProprietario(Restaurante restaurante);
}

