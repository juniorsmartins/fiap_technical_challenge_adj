package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http500.RestauranteNonPersistenceException;
import br.com.fiap.tech.challenge_user.infrastructure.entity.RestauranteEntity;
import br.com.fiap.tech.challenge_user.infrastructure.repository.RestauranteRepository;
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
public class RestauranteCreateAdapter implements CreateOutputPort<RestauranteEntity> {

    private final RestauranteRepository repository;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Modifying
    @Override
    public RestauranteEntity save(@NonNull final RestauranteEntity restauranteEntity) {

        try {
            return repository.saveAndFlush(restauranteEntity);

        } catch (DataIntegrityViolationException e) {
            throw new RestauranteNonPersistenceException(e.getMessage());
        }
    }
}

