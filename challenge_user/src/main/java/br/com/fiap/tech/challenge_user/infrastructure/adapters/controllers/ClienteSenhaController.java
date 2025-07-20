package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.application.interfaces.in.UsuarioSenhaInputPort;
import br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsController;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ClienteDao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_CLIENTE})
public class ClienteSenhaController extends AbstractUsuarioSenhaController<ClienteDao> {

    public ClienteSenhaController(
            UsuarioSenhaInputPort<ClienteDao> senhaInputPort) {
        super(senhaInputPort);
    }
}

