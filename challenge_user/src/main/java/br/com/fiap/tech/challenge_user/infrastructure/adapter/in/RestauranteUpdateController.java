package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.presenters.InputMapper;
import br.com.fiap.tech.challenge_user.infrastructure.presenters.OutputMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.in.UpdateInputPort;
import br.com.fiap.tech.challenge_user.domain.model.Restaurante;
import br.com.fiap.tech.challenge_user.infrastructure.constant.ConstantsController;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.RestauranteDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.RestauranteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.RestauranteEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_RESTAURANTE})
public class RestauranteUpdateController
        extends AbstractUpdateController<RestauranteDtoRequest, RestauranteDtoResponse, Restaurante, RestauranteEntity> {

    public RestauranteUpdateController(
            InputMapper<RestauranteDtoRequest, Restaurante> inputMapper,
            OutputMapper<Restaurante, RestauranteDtoResponse, RestauranteEntity> outputMapper,
            UpdateInputPort<Restaurante> updateInputPort) {
        super(inputMapper, outputMapper, updateInputPort);
    }
}

