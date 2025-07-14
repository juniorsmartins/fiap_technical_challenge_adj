package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsController;
import br.com.fiap.tech.challenge_user.domain.models.Proprietario;
import br.com.fiap.tech.challenge_user.application.interfaces.in.DeleteByIdInputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_PROPRIETARIO})
public class ProprietarioDeleteController extends AbstractDeleteController<Proprietario> {

    public ProprietarioDeleteController(DeleteByIdInputPort<Proprietario> deleteByIdInputPort) {
        super(deleteByIdInputPort);
    }
}

