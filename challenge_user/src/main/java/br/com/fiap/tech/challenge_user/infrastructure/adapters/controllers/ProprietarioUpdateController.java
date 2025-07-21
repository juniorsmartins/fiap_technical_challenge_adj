package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputPresenter;
import br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsController;
import br.com.fiap.tech.challenge_user.application.dtos.in.ProprietarioDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import br.com.fiap.tech.challenge_user.domain.entities.Proprietario;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.InputPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.in.UpdateInputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_PROPRIETARIO})
public class ProprietarioUpdateController
        extends AbstractUpdateController<ProprietarioDtoRequest, ProprietarioDtoResponse, Proprietario, ProprietarioDao> {

    public ProprietarioUpdateController(
            InputPresenter<ProprietarioDtoRequest, Proprietario> inputPresenter,
            OutputPresenter<Proprietario, ProprietarioDtoResponse, ProprietarioDao> outputPresenter,
            UpdateInputPort<Proprietario> updateInputPort) {
        super(inputPresenter, outputPresenter, updateInputPort);
    }
}

