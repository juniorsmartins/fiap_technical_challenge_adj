package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.application.interfaces.in.UsuarioSenhaInputPort;
import br.com.fiap.tech.challenge_user.infrastructure.constant.ConstantsController;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_PROPRIETARIO})
public class ProprietarioSenhaController extends AbstractUsuarioSenhaController<ProprietarioEntity> {

    public ProprietarioSenhaController(
            UsuarioSenhaInputPort<ProprietarioEntity> senhaInputPort) {
        super(senhaInputPort);
    }
}

