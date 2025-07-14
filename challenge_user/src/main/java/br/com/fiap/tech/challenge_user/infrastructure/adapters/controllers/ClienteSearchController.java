package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.PageMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.out.UsuarioSearchOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.constant.ConstantsController;
import br.com.fiap.tech.challenge_user.application.dtos.out.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ClienteEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_CLIENTE})
public class ClienteSearchController extends AbstractUsuarioSearchController<ClienteDtoResponse, ClienteEntity> {

    public ClienteSearchController(
            PageMapper<ClienteDtoResponse, ClienteEntity> mapper,
            UsuarioSearchOutputPort<ClienteEntity> outputPort) {
        super(mapper, outputPort);
    }
}

