package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.constant.ControllerConstants;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.ClienteDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.domain.model.Cliente;
import br.com.fiap.tech.challenge_user.application.mapper.InputMapper;
import br.com.fiap.tech.challenge_user.application.mapper.OutputMapper;
import br.com.fiap.tech.challenge_user.application.port.in.UsuarioUpdateInputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ControllerConstants.URI_CLIENTE})
public class ClienteUpdateController
        extends AbstractUsuarioUpdateController<ClienteDtoRequest, ClienteDtoResponse, Cliente, ClienteEntity> {

    public ClienteUpdateController(
            InputMapper<ClienteDtoRequest, Cliente> inputMapper,
            OutputMapper<Cliente, ClienteDtoResponse, ClienteEntity> outputMapper,
            UsuarioUpdateInputPort<Cliente> updateInputPort) {
        super(inputMapper, outputMapper, updateInputPort);
    }
}

