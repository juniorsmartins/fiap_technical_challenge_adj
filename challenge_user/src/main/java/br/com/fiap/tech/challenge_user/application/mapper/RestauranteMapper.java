package br.com.fiap.tech.challenge_user.application.mapper;

import br.com.fiap.tech.challenge_user.domain.model.Restaurante;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.RestauranteDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.RestauranteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.RestauranteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class RestauranteMapper implements InputMapper<RestauranteDtoRequest, Restaurante>,
        EntityMapper<Restaurante, RestauranteEntity>, OutputMapper<Restaurante, RestauranteDtoResponse, RestauranteEntity> {

    @Override
    public Restaurante toDomainIn(RestauranteDtoRequest dto) {
        if (dto == null) {
            return null;
        }

        return new Restaurante(null, dto.nome());
    }

    @Override
    public RestauranteDtoResponse toDtoResponse(Restaurante domain) {
        if (domain == null) {
            return null;
        }

        return new RestauranteDtoResponse(domain.getRestauranteId(), domain.getNome());
    }

    @Override
    public RestauranteDtoResponse toResponse(RestauranteEntity entity) {
        if (entity == null) {
            return null;
        }

        return new RestauranteDtoResponse(entity.getRestauranteId(), entity.getNome());
    }

    @Override
    public Page<RestauranteDtoResponse> toPageResponse(Page<RestauranteEntity> entityPage) {
        return null;
    }

    @Override
    public RestauranteEntity toEntity(Restaurante domain) {
        if (domain == null) {
            return null;
        }

        return new RestauranteEntity(domain.getRestauranteId(), domain.getNome());
    }

    @Override
    public Restaurante toDomain(RestauranteEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Restaurante(entity.getRestauranteId(), entity.getNome());
    }
}

