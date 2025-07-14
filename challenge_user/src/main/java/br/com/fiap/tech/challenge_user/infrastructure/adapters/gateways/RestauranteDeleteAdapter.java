package br.com.fiap.tech.challenge_user.infrastructure.adapters.gateways;

import br.com.fiap.tech.challenge_user.application.interfaces.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.RestauranteEntity;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.RestauranteRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class RestauranteDeleteAdapter implements DeleteOutputPort<RestauranteEntity> {

    private final RestauranteRepository repository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Override
    public void delete(@NonNull final RestauranteEntity entity) {

        repository.delete(entity);
    }
}

