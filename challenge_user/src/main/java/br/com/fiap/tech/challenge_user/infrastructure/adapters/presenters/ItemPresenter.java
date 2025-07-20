package br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters;

import br.com.fiap.tech.challenge_user.domain.entities.Item;
import br.com.fiap.tech.challenge_user.application.dtos.in.ItemDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.ItemDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ItemDao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemPresenter extends InputPresenter<ItemDtoRequest, Item>,
        OutputPresenter<Item, ItemDtoResponse,
                        ItemDao>, DaoPresenter<Item, ItemDao> {

    @Mapping(target = "itemId", ignore = true)
    Item toDomainIn(ItemDtoRequest dto);

    ItemDtoResponse toDtoResponse(Item domain);

    ItemDtoResponse toResponse(ItemDao entity);

    ItemDao toEntity(Item domain);

    Item toDomain(ItemDao entity);
}

