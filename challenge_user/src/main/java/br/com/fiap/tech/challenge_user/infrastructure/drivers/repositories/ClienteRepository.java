package br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories;

import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ClienteDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteDao, UUID>, JpaSpecificationExecutor<ClienteDao> {

    Optional<ClienteDao> findByEmail(String email);
}

