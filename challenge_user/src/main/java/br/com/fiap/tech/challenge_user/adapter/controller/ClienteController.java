package br.com.fiap.tech.challenge_user.adapter.controller;

import br.com.fiap.tech.challenge_user.adapter.dto.request.ClienteDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.request.ClienteUpdateDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.response.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.adapter.mapper.InputMapper;
import br.com.fiap.tech.challenge_user.adapter.mapper.OutputMapper;
import br.com.fiap.tech.challenge_user.application.core.domain.Cliente;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioCreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioDeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ClienteController.URI_CLIENTE})
public class ClienteController
        extends AbstractUsuarioController<ClienteDtoRequest, ClienteDtoResponse, ClienteUpdateDtoRequest, Cliente, ClienteEntity> {

    protected static final String URI_CLIENTE = "/api/v1/challenge-user/clientes";

    public ClienteController(
            InputMapper<ClienteDtoRequest, ClienteUpdateDtoRequest, Cliente> inputMapper,
            OutputMapper<Cliente, ClienteDtoResponse, ClienteEntity> outputMapper,
            UsuarioCreateInputPort<Cliente> createInputPort,
            UsuarioUpdateInputPort<Cliente> updateInputPort,
            UsuarioFindByIdOutputPort<ClienteEntity> findByIdOutputPort,
            UsuarioDeleteByIdInputPort<Cliente> deleteByIdInputPort) {
        super(inputMapper, outputMapper, createInputPort, updateInputPort, findByIdOutputPort, deleteByIdInputPort);
    }
}

