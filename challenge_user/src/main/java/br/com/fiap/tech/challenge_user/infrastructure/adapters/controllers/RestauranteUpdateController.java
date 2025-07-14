package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.InputMapper;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.in.UpdateInputPort;
import br.com.fiap.tech.challenge_user.domain.models.Restaurante;
import br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsController;
import br.com.fiap.tech.challenge_user.application.dtos.in.RestauranteDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.RestauranteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.RestauranteEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_RESTAURANTE})
public class RestauranteUpdateController
        extends AbstractUpdateController<RestauranteDtoRequest, RestauranteDtoResponse, Restaurante, RestauranteEntity> {

    public RestauranteUpdateController(
            InputMapper<RestauranteDtoRequest, Restaurante> inputMapper,
            OutputMapper<Restaurante, RestauranteDtoResponse, RestauranteEntity> outputMapper,
            UpdateInputPort<Restaurante> updateInputPort) {
        super(inputMapper, outputMapper, updateInputPort);
    }
}

