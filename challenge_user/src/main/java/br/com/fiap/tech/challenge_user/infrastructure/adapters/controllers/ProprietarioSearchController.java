package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.PageMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.out.UsuarioSearchOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.constant.ConstantsController;
import br.com.fiap.tech.challenge_user.application.dtos.out.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ProprietarioEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_PROPRIETARIO})
public class ProprietarioSearchController
        extends AbstractUsuarioSearchController<ProprietarioDtoResponse, ProprietarioEntity> {

    public ProprietarioSearchController(
            PageMapper<ProprietarioDtoResponse, ProprietarioEntity> mapper,
            UsuarioSearchOutputPort<ProprietarioEntity> outputPort) {
        super(mapper, outputPort);
    }
}

