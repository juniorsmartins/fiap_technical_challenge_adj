package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.interfaces.in.DeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.models.Item;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ItemDao;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItemDeleteUseCase extends AbstractDeleteUseCase<ItemDao> implements DeleteByIdInputPort<Item> {

    public ItemDeleteUseCase(
            FindByIdOutputPort<ItemDao> findByIdOutputPort,
            DeleteOutputPort<ItemDao> deleteOutputPort) {
        super(findByIdOutputPort, deleteOutputPort);
    }

    @Override
    public void deleteById(@NonNull final UUID id) {

        super.deleteById(id);
    }
}

