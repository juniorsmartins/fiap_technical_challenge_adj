package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.in.UpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.exception.http404.ItemNotFoundException;
import br.com.fiap.tech.challenge_user.domain.model.Item;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ItemEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemUpdateUseCase implements UpdateInputPort<Item> {

    private final EntityMapper<Item, ItemEntity> entityMapper;

    private final CreateOutputPort<ItemEntity> createOutputPort;

    private final FindByIdOutputPort<ItemEntity> findByIdOutputPort;

    @Override
    public Item update(@NonNull final UUID id, @NonNull Item domain) {

        return findByIdOutputPort.findById(id)
                .map(entity -> this.updateItem(domain, entity))
                .map(createOutputPort::save)
                .map(entityMapper::toDomain)
                .orElseThrow(() -> {
                    log.error("ItemUpdateUseCase - Item não encontrado por id: {}", id);
                    return new ItemNotFoundException(id);
                });
    }

    private ItemEntity updateItem(Item domain, ItemEntity entity) {
        BeanUtils.copyProperties(domain, entity, "itemId");
        return entity;
    }
}

