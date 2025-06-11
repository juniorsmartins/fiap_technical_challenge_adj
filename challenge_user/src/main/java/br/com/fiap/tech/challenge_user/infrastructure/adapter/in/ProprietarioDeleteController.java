package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.constant.ControllerConstants;
import br.com.fiap.tech.challenge_user.domain.model.Proprietario;
import br.com.fiap.tech.challenge_user.application.port.in.UsuarioDeleteByIdInputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ControllerConstants.URI_PROPRIETARIO})
public class ProprietarioDeleteController extends AbstractUsuarioDeleteController<Proprietario> {

    public ProprietarioDeleteController(UsuarioDeleteByIdInputPort<Proprietario> deleteByIdInputPort) {
        super(deleteByIdInputPort);
    }
}

