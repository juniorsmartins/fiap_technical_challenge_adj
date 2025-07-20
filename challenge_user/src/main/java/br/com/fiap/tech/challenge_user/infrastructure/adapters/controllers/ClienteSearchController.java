package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.PageMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.out.UsuarioSearchOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsController;
import br.com.fiap.tech.challenge_user.application.dtos.out.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ClienteDao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_CLIENTE})
public class ClienteSearchController extends AbstractUsuarioSearchController<ClienteDtoResponse, ClienteDao> {

    public ClienteSearchController(
            PageMapper<ClienteDtoResponse, ClienteDao> mapper,
            UsuarioSearchOutputPort<ClienteDao> outputPort) {
        super(mapper, outputPort);
    }
}

