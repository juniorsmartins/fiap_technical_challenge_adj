package br.com.fiap.tech.challenge_user.application.mapper;

import br.com.fiap.tech.challenge_user.domain.model.Item;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.ItemDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.ItemDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ItemMapper extends InputMapper<ItemDtoRequest, Item>, OutputMapper<Item, ItemDtoResponse,
        ItemEntity>, EntityMapper<Item, ItemEntity> {

    @Mapping(target = "itemId", ignore = true)
    Item toDomainIn(ItemDtoRequest dto);

    ItemDtoResponse toDtoResponse(Item domain);

    ItemDtoResponse toResponse(ItemEntity entity);

    default Page<ItemDtoResponse> toPageResponse(Page<ItemEntity> entityPage) {
        return null;
    };

    ItemEntity toEntity(Item domain);

    Item toDomain(ItemEntity entity);
}

