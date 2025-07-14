package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.models.Restaurante;
import br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsController;
import br.com.fiap.tech.challenge_user.application.dtos.out.RestauranteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.RestauranteEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_RESTAURANTE})
public class RestauranteFindController extends AbstractFindController<RestauranteDtoResponse, Restaurante, RestauranteEntity> {

    public RestauranteFindController(
            OutputMapper<Restaurante, RestauranteDtoResponse, RestauranteEntity> outputMapper,
            FindByIdOutputPort<RestauranteEntity> findByIdOutputPort) {
        super(outputMapper, findByIdOutputPort);
    }
}

