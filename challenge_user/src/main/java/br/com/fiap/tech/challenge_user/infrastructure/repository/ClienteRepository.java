package br.com.fiap.tech.challenge_user.infrastructure.repository;

import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID> {

    Optional<ClienteEntity> findByEmail(String email);
}

