package br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters;

import br.com.fiap.tech.challenge_user.domain.models.Item;
import br.com.fiap.tech.challenge_user.application.dtos.in.ItemDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.ItemDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper extends InputMapper<ItemDtoRequest, Item>,
        OutputMapper<Item, ItemDtoResponse,
        ItemEntity>, EntityMapper<Item, ItemEntity> {

    @Mapping(target = "itemId", ignore = true)
    Item toDomainIn(ItemDtoRequest dto);

    ItemDtoResponse toDtoResponse(Item domain);

    ItemDtoResponse toResponse(ItemEntity entity);

    ItemEntity toEntity(Item domain);

    Item toDomain(ItemEntity entity);
}

