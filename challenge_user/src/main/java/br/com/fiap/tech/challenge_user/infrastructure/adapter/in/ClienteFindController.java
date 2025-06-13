package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.constant.ControllerConstants;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.domain.model.Cliente;
import br.com.fiap.tech.challenge_user.application.mapper.OutputMapper;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ControllerConstants.URI_CLIENTE})
public class ClienteFindController extends AbstractFindController<ClienteDtoResponse, Cliente, ClienteEntity> {

    public ClienteFindController(
            OutputMapper<Cliente, ClienteDtoResponse, ClienteEntity> outputMapper,
            FindByIdOutputPort<ClienteEntity> findByIdOutputPort) {
        super(outputMapper, findByIdOutputPort);
    }
}

