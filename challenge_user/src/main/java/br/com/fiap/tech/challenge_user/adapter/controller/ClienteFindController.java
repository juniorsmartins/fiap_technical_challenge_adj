package br.com.fiap.tech.challenge_user.adapter.controller;

import br.com.fiap.tech.challenge_user.adapter.constant.ControllerConstants;
import br.com.fiap.tech.challenge_user.adapter.dto.response.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Cliente;
import br.com.fiap.tech.challenge_user.application.mapper.OutputMapper;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ControllerConstants.URI_CLIENTE})
public class ClienteFindController extends AbstractUsuarioFindController<ClienteDtoResponse, Cliente, ClienteEntity> {

    public ClienteFindController(
            OutputMapper<Cliente, ClienteDtoResponse, ClienteEntity> outputMapper,
            UsuarioFindByIdOutputPort<ClienteEntity> findByIdOutputPort) {
        super(outputMapper, findByIdOutputPort);
    }
}

