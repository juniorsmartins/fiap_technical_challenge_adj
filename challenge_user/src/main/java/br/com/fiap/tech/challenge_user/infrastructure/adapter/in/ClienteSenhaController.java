package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.application.port.in.UsuarioSenhaInputPort;
import br.com.fiap.tech.challenge_user.infrastructure.constant.ConstantsController;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_CLIENTE})
public class ClienteSenhaController extends AbstractUsuarioSenhaController<ClienteEntity> {

    public ClienteSenhaController(
            UsuarioSenhaInputPort<ClienteEntity> senhaInputPort) {
        super(senhaInputPort);
    }
}

