package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.constant.ControllerConstants;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.ProprietarioDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.application.domain.model.Proprietario;
import br.com.fiap.tech.challenge_user.application.mapper.InputMapper;
import br.com.fiap.tech.challenge_user.application.mapper.OutputMapper;
import br.com.fiap.tech.challenge_user.application.port.in.UsuarioCreateInputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ControllerConstants.URI_PROPRIETARIO})
public class ProprietarioCreateController
        extends AbstractUsuarioCreateController<ProprietarioDtoRequest, ProprietarioDtoResponse, Proprietario, ProprietarioEntity> {

    public ProprietarioCreateController(
            InputMapper<ProprietarioDtoRequest, Proprietario> inputMapper,
            OutputMapper<Proprietario, ProprietarioDtoResponse, ProprietarioEntity> outputMapper,
            UsuarioCreateInputPort<Proprietario> createInputPort) {
        super(inputMapper, outputMapper, createInputPort);
    }
}

