package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsController;
import br.com.fiap.tech.challenge_user.application.dtos.out.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ClienteDao;
import br.com.fiap.tech.challenge_user.domain.entities.Cliente;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_CLIENTE})
public class ClienteFindController extends AbstractFindController<ClienteDtoResponse, Cliente, ClienteDao> {

    public ClienteFindController(
            OutputPresenter<Cliente, ClienteDtoResponse, ClienteDao> outputPresenter,
            FindByIdOutputPort<ClienteDao> findByIdOutputPort) {
        super(outputPresenter, findByIdOutputPort);
    }
}

