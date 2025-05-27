package br.com.fiap.tech.challenge_user.adapter.controller;

import br.com.fiap.tech.challenge_user.adapter.constant.ControllerConstants;
import br.com.fiap.tech.challenge_user.adapter.dto.response.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Proprietario;
import br.com.fiap.tech.challenge_user.application.mapper.OutputMapper;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ControllerConstants.URI_PROPRIETARIO})
public class ProprietarioFindController
        extends AbstractUsuarioFindController<ProprietarioDtoResponse, Proprietario, ProprietarioEntity> {

    public ProprietarioFindController(
            OutputMapper<Proprietario, ProprietarioDtoResponse, ProprietarioEntity> outputMapper,
            UsuarioFindByIdOutputPort<ProprietarioEntity> findByIdOutputPort) {
        super(outputMapper, findByIdOutputPort);
    }
}

