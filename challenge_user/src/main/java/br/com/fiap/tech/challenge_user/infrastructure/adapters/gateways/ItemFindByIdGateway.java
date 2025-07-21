package br.com.fiap.tech.challenge_user.infrastructure.adapters.gateways;

import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ItemDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.ItemRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ItemFindByIdGateway implements FindByIdOutputPort<ItemDao> {

    private final ItemRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Optional<ItemDao> findById(@NonNull final UUID id) {

        return repository.findById(id);
    }
}

