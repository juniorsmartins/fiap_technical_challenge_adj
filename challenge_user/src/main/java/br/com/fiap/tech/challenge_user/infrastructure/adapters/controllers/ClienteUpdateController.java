package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsController;
import br.com.fiap.tech.challenge_user.application.dtos.in.ClienteDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ClienteDao;
import br.com.fiap.tech.challenge_user.domain.models.Cliente;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.InputMapper;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.in.UpdateInputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_CLIENTE})
public class ClienteUpdateController
        extends AbstractUpdateController<ClienteDtoRequest, ClienteDtoResponse, Cliente, ClienteDao> {

    public ClienteUpdateController(
            InputMapper<ClienteDtoRequest, Cliente> inputMapper,
            OutputMapper<Cliente, ClienteDtoResponse, ClienteDao> outputMapper,
            UpdateInputPort<Cliente> updateInputPort) {
        super(inputMapper, outputMapper, updateInputPort);
    }
}

