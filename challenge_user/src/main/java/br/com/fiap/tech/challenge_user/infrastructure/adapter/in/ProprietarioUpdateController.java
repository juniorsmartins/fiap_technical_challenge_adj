package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.constant.ConstantsController;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.ProprietarioDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.domain.model.Proprietario;
import br.com.fiap.tech.challenge_user.application.mapper.InputMapper;
import br.com.fiap.tech.challenge_user.application.mapper.OutputMapper;
import br.com.fiap.tech.challenge_user.application.port.in.UpdateInputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_PROPRIETARIO})
public class ProprietarioUpdateController
        extends AbstractUpdateController<ProprietarioDtoRequest, ProprietarioDtoResponse, Proprietario, ProprietarioEntity> {

    public ProprietarioUpdateController(
            InputMapper<ProprietarioDtoRequest, Proprietario> inputMapper,
            OutputMapper<Proprietario, ProprietarioDtoResponse, ProprietarioEntity> outputMapper,
            UpdateInputPort<Proprietario> updateInputPort) {
        super(inputMapper, outputMapper, updateInputPort);
    }
}

