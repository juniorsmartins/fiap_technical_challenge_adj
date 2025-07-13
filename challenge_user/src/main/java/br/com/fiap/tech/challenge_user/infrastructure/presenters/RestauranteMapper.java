package br.com.fiap.tech.challenge_user.infrastructure.presenters;

import br.com.fiap.tech.challenge_user.domain.model.Proprietario;
import br.com.fiap.tech.challenge_user.domain.model.Restaurante;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.RestauranteDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.RestauranteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.RestauranteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {EnderecoMapper.class, ProprietarioMapper.class})
public abstract class RestauranteMapper implements InputMapper<RestauranteDtoRequest, Restaurante>,
        OutputMapper<Restaurante, RestauranteDtoResponse, RestauranteEntity>,
        EntityMapper<Restaurante, RestauranteEntity> {

    @Autowired
    private ProprietarioMapper proprietarioMapper;

    @Mapping(target = "restauranteId", ignore = true)
    @Mapping(target = "proprietario", source = "proprietario", qualifiedByName = "mapUuidToProprietario")
    public abstract Restaurante toDomainIn(RestauranteDtoRequest dto);

    public abstract RestauranteDtoResponse toDtoResponse(Restaurante domain);

    public abstract RestauranteDtoResponse toResponse(RestauranteEntity entity);

    public abstract RestauranteEntity toEntity(Restaurante domain);

    public abstract Restaurante toDomain(RestauranteEntity entity);

    @Named("mapUuidToProprietario")
    public Proprietario map(UUID proprietarioId) {
        if (proprietarioId == null) {
            return null;
        }

        Proprietario proprietario = new Proprietario();
        proprietario.setUsuarioId(proprietarioId);

        return proprietario;
    }
}

