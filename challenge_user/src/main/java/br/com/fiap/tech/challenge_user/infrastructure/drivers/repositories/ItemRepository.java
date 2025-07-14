package br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories;

import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, UUID> {

    Optional<ItemEntity> findByNome(String nome);
}

