package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.application.interfaces.in.UsuarioSenhaInputPort;
import br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsController;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_PROPRIETARIO})
public class ProprietarioSenhaController extends AbstractUsuarioSenhaController<ProprietarioDao> {

    public ProprietarioSenhaController(
            UsuarioSenhaInputPort<ProprietarioDao> senhaInputPort) {
        super(senhaInputPort);
    }
}

