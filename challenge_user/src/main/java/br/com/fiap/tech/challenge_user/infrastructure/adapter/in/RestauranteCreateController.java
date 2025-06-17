package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.application.mapper.InputMapper;
import br.com.fiap.tech.challenge_user.application.mapper.OutputMapper;
import br.com.fiap.tech.challenge_user.application.port.in.CreateInputPort;
import br.com.fiap.tech.challenge_user.domain.model.Restaurante;
import br.com.fiap.tech.challenge_user.infrastructure.constant.ConstantsController;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.RestauranteDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.RestauranteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.RestauranteEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_RESTAURANTE})
public class RestauranteCreateController
        extends AbstractCreateController<RestauranteDtoRequest, RestauranteDtoResponse, Restaurante, RestauranteEntity> {

    public RestauranteCreateController(
            InputMapper<RestauranteDtoRequest, Restaurante> inputMapper,
            OutputMapper<Restaurante, RestauranteDtoResponse, RestauranteEntity> outputMapper,
            CreateInputPort<Restaurante> createInputPort) {
        super(inputMapper, outputMapper, createInputPort);
    }
}

