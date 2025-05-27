package br.com.fiap.tech.challenge_user.adapter.controller;

import br.com.fiap.tech.challenge_user.application.core.domain.Cliente;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioDeleteByIdInputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ClienteDeleteController.URI_CLIENTE})
public class ClienteDeleteController extends AbstractUsuarioDeleteController<Cliente> {

    protected static final String URI_CLIENTE = "/api/v1/challenge-user/clientes";

    public ClienteDeleteController(UsuarioDeleteByIdInputPort<Cliente> deleteByIdInputPort) {
        super(deleteByIdInputPort);
    }
}

