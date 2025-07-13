package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.constant.ConstantsController;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.domain.model.Proprietario;
import br.com.fiap.tech.challenge_user.infrastructure.presenters.OutputMapper;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_PROPRIETARIO})
public class ProprietarioFindController
        extends AbstractFindController<ProprietarioDtoResponse, Proprietario, ProprietarioEntity> {

    public ProprietarioFindController(
            OutputMapper<Proprietario, ProprietarioDtoResponse, ProprietarioEntity> outputMapper,
            FindByIdOutputPort<ProprietarioEntity> findByIdOutputPort) {
        super(outputMapper, findByIdOutputPort);
    }
}

