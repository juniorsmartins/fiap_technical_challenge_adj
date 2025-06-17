package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.application.mapper.OutputMapper;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioSearchOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.constant.ConstantsController;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_PROPRIETARIO})
public class ProprietarioSearchController
        extends AbstractUsuarioSearchController<ProprietarioDtoResponse, ProprietarioEntity> {

    public ProprietarioSearchController(
            OutputMapper<?, ProprietarioDtoResponse, ProprietarioEntity> mapper,
            UsuarioSearchOutputPort<ProprietarioEntity> outputPort) {
        super(mapper, outputPort);
    }
}

