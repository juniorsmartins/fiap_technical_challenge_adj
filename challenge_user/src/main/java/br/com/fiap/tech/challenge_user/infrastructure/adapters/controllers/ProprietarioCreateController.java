package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsController;
import br.com.fiap.tech.challenge_user.application.dtos.in.ProprietarioDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import br.com.fiap.tech.challenge_user.domain.entities.Proprietario;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.InputPresenter;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.in.CreateInputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_PROPRIETARIO})
public class ProprietarioCreateController
        extends AbstractCreateController<ProprietarioDtoRequest, ProprietarioDtoResponse, Proprietario, ProprietarioDao> {

    public ProprietarioCreateController(
            InputPresenter<ProprietarioDtoRequest, Proprietario> inputPresenter,
            OutputPresenter<Proprietario, ProprietarioDtoResponse, ProprietarioDao> outputPresenter,
            CreateInputPort<Proprietario> createInputPort) {
        super(inputPresenter, outputPresenter, createInputPort);
    }
}

