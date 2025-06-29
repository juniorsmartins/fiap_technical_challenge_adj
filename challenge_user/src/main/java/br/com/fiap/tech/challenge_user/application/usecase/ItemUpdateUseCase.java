package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.in.UpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.model.Item;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ItemEntity;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public final class ItemUpdateUseCase extends AbstractUpdateUseCase<Item, ItemEntity> implements UpdateInputPort<Item> {

    public ItemUpdateUseCase(
            EntityMapper<Item, ItemEntity> entityMapper,
            CreateOutputPort<ItemEntity> createOutputPort,
            FindByIdOutputPort<ItemEntity> findByIdOutputPort) {
        super(entityMapper, createOutputPort, findByIdOutputPort);
    }

    @Override
    public Item update(@NonNull final UUID id, @NonNull Item domain) {

        return super.update(id, domain);
    }

    @Override
    public ItemEntity rulesUpdate(UUID id, Item domain, ItemEntity entity) {
        domain.setItemId(id);
        BeanUtils.copyProperties(domain, entity, "itemId");
        return entity;
    }
}

