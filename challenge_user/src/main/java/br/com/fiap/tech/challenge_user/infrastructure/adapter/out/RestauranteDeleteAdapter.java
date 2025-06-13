package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.port.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.entity.RestauranteEntity;
import br.com.fiap.tech.challenge_user.infrastructure.repository.RestauranteRepository;
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

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    @Modifying
    @Override
    public void delete(RestauranteEntity entity) {
        repository.delete(entity);
    }
}

