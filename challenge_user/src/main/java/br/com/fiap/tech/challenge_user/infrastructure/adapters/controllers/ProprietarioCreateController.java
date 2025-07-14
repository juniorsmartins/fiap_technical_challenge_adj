package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.constant.ConstantsController;
import br.com.fiap.tech.challenge_user.application.dtos.in.ProprietarioDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.domain.model.Proprietario;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.InputMapper;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.in.CreateInputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_PROPRIETARIO})
public class ProprietarioCreateController
        extends AbstractCreateController<ProprietarioDtoRequest, ProprietarioDtoResponse, Proprietario, ProprietarioEntity> {

    public ProprietarioCreateController(
            InputMapper<ProprietarioDtoRequest, Proprietario> inputMapper,
            OutputMapper<Proprietario, ProprietarioDtoResponse, ProprietarioEntity> outputMapper,
            CreateInputPort<Proprietario> createInputPort) {
        super(inputMapper, outputMapper, createInputPort);
    }
}

