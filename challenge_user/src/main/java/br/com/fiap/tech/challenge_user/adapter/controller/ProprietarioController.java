package br.com.fiap.tech.challenge_user.adapter.controller;

import br.com.fiap.tech.challenge_user.adapter.dto.request.ProprietarioDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.request.ProprietarioUpdateDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.response.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.application.mapper.InputMapper;
import br.com.fiap.tech.challenge_user.application.mapper.OutputMapper;
import br.com.fiap.tech.challenge_user.application.core.domain.Proprietario;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioCreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioDeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ProprietarioController.URI_PROPRIETARIO})
public class ProprietarioController
        extends AbstractUsuarioController<ProprietarioDtoRequest, ProprietarioDtoResponse, ProprietarioUpdateDtoRequest, Proprietario, ProprietarioEntity> {

    protected static final String URI_PROPRIETARIO = "/api/v1/challenge-user/proprietarios";

    public ProprietarioController(
            InputMapper<ProprietarioDtoRequest, ProprietarioUpdateDtoRequest, Proprietario> inputMapper,
            OutputMapper<Proprietario, ProprietarioDtoResponse, ProprietarioEntity> outputMapper,
            UsuarioCreateInputPort<Proprietario> createInputPort,
            UsuarioUpdateInputPort<Proprietario> updateInputPort,
            UsuarioFindByIdOutputPort<ProprietarioEntity> findByIdOutputPort,
            UsuarioDeleteByIdInputPort<Proprietario> deleteByIdInputPort) {
        super(inputMapper, outputMapper, createInputPort, updateInputPort, findByIdOutputPort, deleteByIdInputPort);
    }
}

