package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.in.CreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.domain.model.Item;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ItemEntity;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ItemCreateUseCase extends AbstractCreateUseCase<Item, ItemEntity> implements CreateInputPort<Item> {

    public ItemCreateUseCase(
            EntityMapper<Item, ItemEntity> entityMapper,
            CreateOutputPort<ItemEntity> createOutputPort) {
        super(entityMapper, createOutputPort);
    }

    @Override
    public Item create(@NonNull final Item domain) {

        return super.create(domain);
    }
}

