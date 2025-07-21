package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.DaoPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.in.CreateInputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.domain.entities.Item;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ItemDao;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ItemCreateUseCase extends AbstractCreateUseCase<Item, ItemDao> implements CreateInputPort<Item> {

    public ItemCreateUseCase(
            DaoPresenter<Item, ItemDao> daoPresenter,
            CreateOutputPort<ItemDao> createOutputPort) {
        super(daoPresenter, createOutputPort);
    }

    @Override
    public Item create(@NonNull final Item domain) {

        return super.create(domain);
    }
}

