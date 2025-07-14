package br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories;

import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestauranteRepository extends JpaRepository<RestauranteEntity, UUID> {

    Optional<RestauranteEntity> findByNome(String nome);
}

