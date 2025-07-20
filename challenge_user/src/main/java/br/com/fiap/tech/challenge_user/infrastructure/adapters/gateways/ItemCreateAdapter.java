package br.com.fiap.tech.challenge_user.infrastructure.adapters.gateways;

import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http500.ItemNonPersistenceException;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ItemDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.ItemRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ItemCreateAdapter implements CreateOutputPort<ItemDao> {

    private final ItemRepository repository;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Modifying
    @Override
    public ItemDao save(@NonNull final ItemDao entity) {

        try {
            return repository.saveAndFlush(entity);

        } catch (DataIntegrityViolationException e) {
            throw new ItemNonPersistenceException(e.getMessage());
        }
    }
}

