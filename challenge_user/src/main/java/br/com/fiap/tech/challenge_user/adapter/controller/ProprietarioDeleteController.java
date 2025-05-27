package br.com.fiap.tech.challenge_user.adapter.controller;

import br.com.fiap.tech.challenge_user.adapter.constant.ControllerConstants;
import br.com.fiap.tech.challenge_user.application.core.domain.Proprietario;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioDeleteByIdInputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ControllerConstants.URI_PROPRIETARIO})
public class ProprietarioDeleteController extends AbstractUsuarioDeleteController<Proprietario> {

    public ProprietarioDeleteController(UsuarioDeleteByIdInputPort<Proprietario> deleteByIdInputPort) {
        super(deleteByIdInputPort);
    }
}

