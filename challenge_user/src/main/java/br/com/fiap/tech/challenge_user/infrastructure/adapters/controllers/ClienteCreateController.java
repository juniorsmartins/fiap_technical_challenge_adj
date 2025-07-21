package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsController;
import br.com.fiap.tech.challenge_user.application.dtos.in.ClienteDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ClienteDao;
import br.com.fiap.tech.challenge_user.domain.entities.Cliente;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.InputPresenter;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.in.CreateInputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_CLIENTE})
public class ClienteCreateController
        extends AbstractCreateController<ClienteDtoRequest, ClienteDtoResponse, Cliente, ClienteDao> {

    public ClienteCreateController(
            InputPresenter<ClienteDtoRequest, Cliente> inputPresenter,
            OutputPresenter<Cliente, ClienteDtoResponse, ClienteDao> outputPresenter,
            CreateInputPort<Cliente> createInputPort) {
        super(inputPresenter, outputPresenter, createInputPort);
    }
}

