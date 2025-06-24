package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.port.in.DeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.model.Item;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ItemEntity;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItemDeleteUseCase extends AbstractDeleteUseCase<ItemEntity> implements DeleteByIdInputPort<Item> {

    public ItemDeleteUseCase(
            FindByIdOutputPort<ItemEntity> findByIdOutputPort,
            DeleteOutputPort<ItemEntity> deleteOutputPort) {
        super(findByIdOutputPort, deleteOutputPort);
    }

    @Override
    public void deleteById(@NonNull final UUID id) {

        super.deleteById(id);
    }
}

