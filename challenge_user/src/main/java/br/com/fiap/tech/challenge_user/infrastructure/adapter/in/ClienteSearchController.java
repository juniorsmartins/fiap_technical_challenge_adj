package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.application.mapper.OutputMapper;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioSearchOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.constant.ControllerConstants;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ControllerConstants.URI_CLIENTE})
public class ClienteSearchController extends AbstractUsuarioSearchController<ClienteDtoResponse, ClienteEntity> {

    public ClienteSearchController(
            OutputMapper<?, ClienteDtoResponse, ClienteEntity> mapper,
            UsuarioSearchOutputPort<ClienteEntity> outputPort) {
        super(mapper, outputPort);
    }
}

