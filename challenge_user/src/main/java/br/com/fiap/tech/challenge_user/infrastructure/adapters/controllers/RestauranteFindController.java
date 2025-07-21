package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.entities.Restaurante;
import br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsController;
import br.com.fiap.tech.challenge_user.application.dtos.out.RestauranteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.RestauranteDao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_RESTAURANTE})
public class RestauranteFindController extends AbstractFindController<RestauranteDtoResponse, Restaurante, RestauranteDao> {

    public RestauranteFindController(
            OutputPresenter<Restaurante, RestauranteDtoResponse, RestauranteDao> outputPresenter,
            FindByIdOutputPort<RestauranteDao> findByIdOutputPort) {
        super(outputPresenter, findByIdOutputPort);
    }
}

