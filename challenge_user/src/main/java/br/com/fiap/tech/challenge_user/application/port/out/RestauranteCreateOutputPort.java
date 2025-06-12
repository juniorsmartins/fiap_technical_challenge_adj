package br.com.fiap.tech.challenge_user.application.port.out;

import br.com.fiap.tech.challenge_user.infrastructure.entity.RestauranteEntity;

public interface RestauranteCreateOutputPort {

    RestauranteEntity save(RestauranteEntity restauranteEntity);
}

