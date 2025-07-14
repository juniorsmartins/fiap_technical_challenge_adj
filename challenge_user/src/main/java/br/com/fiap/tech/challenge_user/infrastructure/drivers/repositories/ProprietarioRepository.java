package br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories;

import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ProprietarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProprietarioRepository extends JpaRepository<ProprietarioEntity, UUID>,
        JpaSpecificationExecutor<ProprietarioEntity> {

    Optional<ProprietarioEntity> findByEmail(String email);
}

