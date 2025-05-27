package br.com.fiap.tech.challenge_user.adapter.controller;

import br.com.fiap.tech.challenge_user.adapter.dto.request.ClienteDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.request.ClienteUpdateDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.response.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Cliente;
import br.com.fiap.tech.challenge_user.application.mapper.InputMapper;
import br.com.fiap.tech.challenge_user.application.mapper.OutputMapper;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioUpdateInputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ClienteUpdateController.URI_CLIENTE})
public class ClienteUpdateController
        extends AbstractUsuarioUpdateController<ClienteDtoRequest, ClienteDtoResponse, ClienteUpdateDtoRequest, Cliente, ClienteEntity> {

    protected static final String URI_CLIENTE = "/api/v1/challenge-user/clientes";

    public ClienteUpdateController(
            InputMapper<ClienteDtoRequest, ClienteUpdateDtoRequest, Cliente> inputMapper,
            OutputMapper<Cliente, ClienteDtoResponse, ClienteEntity> outputMapper,
            UsuarioUpdateInputPort<Cliente> updateInputPort) {
        super(inputMapper, outputMapper, updateInputPort);
    }
}

