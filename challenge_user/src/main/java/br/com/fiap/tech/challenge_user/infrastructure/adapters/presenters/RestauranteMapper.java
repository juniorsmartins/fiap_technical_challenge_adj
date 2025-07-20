package br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters;

import br.com.fiap.tech.challenge_user.domain.models.Proprietario;
import br.com.fiap.tech.challenge_user.domain.models.Restaurante;
import br.com.fiap.tech.challenge_user.application.dtos.in.RestauranteDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.RestauranteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.RestauranteDao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {EnderecoMapper.class, ProprietarioMapper.class})
public abstract class RestauranteMapper implements InputMapper<RestauranteDtoRequest, Restaurante>,
        OutputMapper<Restaurante, RestauranteDtoResponse, RestauranteDao>,
        EntityMapper<Restaurante, RestauranteDao> {

    @Autowired
    private ProprietarioMapper proprietarioMapper;

    @Mapping(target = "restauranteId", ignore = true)
    @Mapping(target = "proprietario", source = "proprietario", qualifiedByName = "mapUuidToProprietario")
    public abstract Restaurante toDomainIn(RestauranteDtoRequest dto);

    public abstract RestauranteDtoResponse toDtoResponse(Restaurante domain);

    public abstract RestauranteDtoResponse toResponse(RestauranteDao entity);

    public abstract RestauranteDao toEntity(Restaurante domain);

    public abstract Restaurante toDomain(RestauranteDao entity);

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

