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

    private final EnderecoMapper mapper;

    @Override
    public Restaurante toDomainIn(RestauranteDtoRequest dto) {
        if (dto == null) {
            return null;
        }

        var endereco = mapper.toEndereco(dto.endereco());

        return new Restaurante(null, dto.nome(), endereco);
    }

    @Override
    public RestauranteDtoResponse toDtoResponse(Restaurante domain) {
        if (domain == null) {
            return null;
        }

        var endereco = mapper.toEnderecoDtoResponse(domain.getEndereco());

        return new RestauranteDtoResponse(domain.getRestauranteId(), domain.getNome(), endereco);
    }

    @Override
    public RestauranteDtoResponse toResponse(RestauranteEntity entity) {
        if (entity == null) {
            return null;
        }

        var endereco = mapper.toEnderecoDtoResponse(entity.getEndereco());

        return new RestauranteDtoResponse(entity.getRestauranteId(), entity.getNome(), endereco);
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

        var endereco = mapper.toEnderecoEntity(domain.getEndereco());

        return new RestauranteEntity(domain.getRestauranteId(), domain.getNome(), endereco);
    }

    @Override
    public Restaurante toDomain(RestauranteEntity entity) {
        if (entity == null) {
            return null;
        }

        var endereco = mapper.toEndereco(entity.getEndereco());

        return new Restaurante(entity.getRestauranteId(), entity.getNome(), endereco);
    }
}

