package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.application.port.in.DeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.domain.model.Restaurante;
import br.com.fiap.tech.challenge_user.infrastructure.constant.ConstantsController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_RESTAURANTE})
public class RestauranteDeleteController extends AbstractDeleteController<Restaurante> {

    public RestauranteDeleteController(DeleteByIdInputPort<Restaurante> deleteByIdInputPort) {
        super(deleteByIdInputPort);
    }
}

