package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsController;
import br.com.fiap.tech.challenge_user.application.dtos.in.ProprietarioDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import br.com.fiap.tech.challenge_user.domain.models.Proprietario;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.InputMapper;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.in.UpdateInputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_PROPRIETARIO})
public class ProprietarioUpdateController
        extends AbstractUpdateController<ProprietarioDtoRequest, ProprietarioDtoResponse, Proprietario, ProprietarioDao> {

    public ProprietarioUpdateController(
            InputMapper<ProprietarioDtoRequest, Proprietario> inputMapper,
            OutputMapper<Proprietario, ProprietarioDtoResponse, ProprietarioDao> outputMapper,
            UpdateInputPort<Proprietario> updateInputPort) {
        super(inputMapper, outputMapper, updateInputPort);
    }
}

