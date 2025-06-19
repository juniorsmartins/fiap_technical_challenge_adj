package br.com.fiap.tech.challenge_user.application.mapper;

import br.com.fiap.tech.challenge_user.domain.model.Proprietario;
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

    private final EnderecoMapper enderecoMapper;

    private final ProprietarioMapper proprietarioMapper;

    @Override
    public Restaurante toDomainIn(RestauranteDtoRequest dto) {
        if (dto == null) {
            return null;
        }

        var endereco = enderecoMapper.toEndereco(dto.endereco());
        var proprietario = new Proprietario();
        proprietario.setUsuarioId(dto.proprietario());

        return new Restaurante(null, dto.nome(), dto.tipoCozinhaEnum(), endereco, proprietario);
    }

    @Override
    public RestauranteDtoResponse toDtoResponse(Restaurante domain) {
        if (domain == null) {
            return null;
        }

        var endereco = enderecoMapper.toEnderecoDtoResponse(domain.getEndereco());
        var proprietario = proprietarioMapper.toDtoResponse(domain.getProprietario());

        return new RestauranteDtoResponse(
                domain.getRestauranteId(), domain.getNome(), domain.getTipoCozinhaEnum(), endereco, proprietario);
    }

    @Override
    public RestauranteDtoResponse toResponse(RestauranteEntity entity) {
        if (entity == null) {
            return null;
        }

        var endereco = enderecoMapper.toEnderecoDtoResponse(entity.getEndereco());
        var proprietario = proprietarioMapper.toResponse(entity.getProprietario());

        return new RestauranteDtoResponse(
                entity.getRestauranteId(), entity.getNome(), entity.getTipoCozinhaEnum(), endereco, proprietario);
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

        var endereco = enderecoMapper.toEnderecoEntity(domain.getEndereco());
        var proprietario = proprietarioMapper.toEntity(domain.getProprietario());

        return new RestauranteEntity(
                domain.getRestauranteId(), domain.getNome(), domain.getTipoCozinhaEnum(), endereco, proprietario);
    }

    @Override
    public Restaurante toDomain(RestauranteEntity entity) {
        if (entity == null) {
            return null;
        }

        var endereco = enderecoMapper.toEndereco(entity.getEndereco());
        var proprietario = proprietarioMapper.toDomain(entity.getProprietario());

        return new Restaurante(
                entity.getRestauranteId(), entity.getNome(), entity.getTipoCozinhaEnum(), endereco, proprietario);
    }
}

