package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.entity.RestauranteEntity;
import br.com.fiap.tech.challenge_user.infrastructure.repository.RestauranteRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RestauranteFindByIdAdapter implements FindByIdOutputPort<RestauranteEntity> {

    private final RestauranteRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Optional<RestauranteEntity> findById(@NonNull final UUID id) {

        return repository.findById(id);
    }
}

