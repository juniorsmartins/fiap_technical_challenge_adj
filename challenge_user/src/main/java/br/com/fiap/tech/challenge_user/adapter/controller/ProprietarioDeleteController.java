package br.com.fiap.tech.challenge_user.adapter.controller;

import br.com.fiap.tech.challenge_user.application.core.domain.Proprietario;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioDeleteByIdInputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ProprietarioDeleteController.URI_PROPRIETARIO})
public class ProprietarioDeleteController extends AbstractUsuarioDeleteController<Proprietario> {

    protected static final String URI_PROPRIETARIO = "/api/v1/challenge-user/proprietarios";

    public ProprietarioDeleteController(UsuarioDeleteByIdInputPort<Proprietario> deleteByIdInputPort) {
        super(deleteByIdInputPort);
    }
}

