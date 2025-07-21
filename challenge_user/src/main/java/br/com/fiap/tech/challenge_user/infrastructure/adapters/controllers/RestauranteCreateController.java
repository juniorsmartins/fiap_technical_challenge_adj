package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.InputPresenter;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.in.CreateInputPort;
import br.com.fiap.tech.challenge_user.domain.entities.Restaurante;
import br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsController;
import br.com.fiap.tech.challenge_user.application.dtos.in.RestauranteDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.RestauranteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.RestauranteDao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_RESTAURANTE})
public class RestauranteCreateController
        extends AbstractCreateController<RestauranteDtoRequest, RestauranteDtoResponse, Restaurante, RestauranteDao> {

    public RestauranteCreateController(
            InputPresenter<RestauranteDtoRequest, Restaurante> inputPresenter,
            OutputPresenter<Restaurante, RestauranteDtoResponse, RestauranteDao> outputPresenter,
            CreateInputPort<Restaurante> createInputPort) {
        super(inputPresenter, outputPresenter, createInputPort);
    }
}

