package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.constant.ControllerConstants;
import br.com.fiap.tech.challenge_user.application.domain.model.Cliente;
import br.com.fiap.tech.challenge_user.application.port.in.UsuarioDeleteByIdInputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ControllerConstants.URI_CLIENTE})
public class ClienteDeleteController extends AbstractUsuarioDeleteController<Cliente> {

    public ClienteDeleteController(UsuarioDeleteByIdInputPort<Cliente> deleteByIdInputPort) {
        super(deleteByIdInputPort);
    }
}

