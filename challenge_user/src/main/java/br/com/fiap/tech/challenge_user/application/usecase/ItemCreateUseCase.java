package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.EntityMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.in.CreateInputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.domain.models.Item;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ItemEntity;
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

