package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsController;
import br.com.fiap.tech.challenge_user.application.dtos.out.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import br.com.fiap.tech.challenge_user.domain.entities.Proprietario;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_PROPRIETARIO})
public class ProprietarioFindController
        extends AbstractFindController<ProprietarioDtoResponse, Proprietario, ProprietarioDao> {

    public ProprietarioFindController(
            OutputMapper<Proprietario, ProprietarioDtoResponse, ProprietarioDao> outputMapper,
            FindByIdOutputPort<ProprietarioDao> findByIdOutputPort) {
        super(outputMapper, findByIdOutputPort);
    }
}

