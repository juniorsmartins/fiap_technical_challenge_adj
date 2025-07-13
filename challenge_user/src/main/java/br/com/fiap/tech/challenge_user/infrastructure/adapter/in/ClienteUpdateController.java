package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.constant.ConstantsController;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.ClienteDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.domain.model.Cliente;
import br.com.fiap.tech.challenge_user.infrastructure.presenters.InputMapper;
import br.com.fiap.tech.challenge_user.infrastructure.presenters.OutputMapper;
import br.com.fiap.tech.challenge_user.application.port.in.UpdateInputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_CLIENTE})
public class ClienteUpdateController
        extends AbstractUpdateController<ClienteDtoRequest, ClienteDtoResponse, Cliente, ClienteEntity> {

    public ClienteUpdateController(
            InputMapper<ClienteDtoRequest, Cliente> inputMapper,
            OutputMapper<Cliente, ClienteDtoResponse, ClienteEntity> outputMapper,
            UpdateInputPort<Cliente> updateInputPort) {
        super(inputMapper, outputMapper, updateInputPort);
    }
}

