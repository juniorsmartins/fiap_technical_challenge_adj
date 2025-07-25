package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.DaoPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.in.UpdateInputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.entities.Item;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ItemDao;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public final class ItemUpdateUseCase extends AbstractUpdateUseCase<Item, ItemDao> implements UpdateInputPort<Item> {

    public ItemUpdateUseCase(
            DaoPresenter<Item, ItemDao> daoPresenter,
            CreateOutputPort<ItemDao> createOutputPort,
            FindByIdOutputPort<ItemDao> findByIdOutputPort) {
        super(daoPresenter, createOutputPort, findByIdOutputPort);
    }

    @Override
    public Item update(@NonNull final UUID id, @NonNull Item domain) {

        return super.update(id, domain);
    }

    @Override
    public ItemDao rulesUpdate(UUID id, Item domain, ItemDao entity) {
        domain.setItemId(id);
        BeanUtils.copyProperties(domain, entity, "itemId");
        return entity;
    }
}

