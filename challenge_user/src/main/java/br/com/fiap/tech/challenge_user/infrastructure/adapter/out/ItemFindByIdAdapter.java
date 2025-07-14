package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ItemEntity;
import br.com.fiap.tech.challenge_user.infrastructure.repository.ItemRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ItemFindByIdAdapter implements FindByIdOutputPort<ItemEntity> {

    private final ItemRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Optional<ItemEntity> findById(@NonNull final UUID id) {

        return repository.findById(id);
    }
}

