package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.constant.ConstantsController;
import br.com.fiap.tech.challenge_user.domain.model.Proprietario;
import br.com.fiap.tech.challenge_user.application.port.in.DeleteByIdInputPort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_PROPRIETARIO})
public class ProprietarioDeleteController extends AbstractDeleteController<Proprietario> {

    public ProprietarioDeleteController(DeleteByIdInputPort<Proprietario> deleteByIdInputPort) {
        super(deleteByIdInputPort);
    }
}

