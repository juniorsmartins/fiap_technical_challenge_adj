package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.application.mapper.OutputMapper;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.model.Restaurante;
import br.com.fiap.tech.challenge_user.infrastructure.constant.ConstantsController;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.RestauranteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.RestauranteEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_RESTAURANTE})
public class RestauranteFindController extends AbstractFindController<RestauranteDtoResponse, Restaurante, RestauranteEntity> {

    public RestauranteFindController(
            OutputMapper<Restaurante, RestauranteDtoResponse, RestauranteEntity> outputMapper,
            FindByIdOutputPort<RestauranteEntity> findByIdOutputPort) {
        super(outputMapper, findByIdOutputPort);
    }
}

