package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.PageMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.out.UsuarioSearchOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsController;
import br.com.fiap.tech.challenge_user.application.dtos.out.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_PROPRIETARIO})
public class ProprietarioSearchController
        extends AbstractUsuarioSearchController<ProprietarioDtoResponse, ProprietarioDao> {

    public ProprietarioSearchController(
            PageMapper<ProprietarioDtoResponse, ProprietarioDao> mapper,
            UsuarioSearchOutputPort<ProprietarioDao> outputPort) {
        super(mapper, outputPort);
    }
}

