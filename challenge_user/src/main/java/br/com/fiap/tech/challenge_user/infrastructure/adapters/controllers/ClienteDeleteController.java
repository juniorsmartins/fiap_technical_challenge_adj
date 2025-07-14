package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsController;
import br.com.fiap.tech.challenge_user.domain.models.Cliente;
import br.com.fiap.tech.challenge_user.application.interfaces.in.DeleteByIdInputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_CLIENTE})
public class ClienteDeleteController extends AbstractDeleteController<Cliente> {

    public ClienteDeleteController(DeleteByIdInputPort<Cliente> deleteByIdInputPort) {
        super(deleteByIdInputPort);
    }
}

