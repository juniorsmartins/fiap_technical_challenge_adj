package br.com.fiap.tech.challenge_user.infrastructure.repository;

import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProprietarioRepository extends JpaRepository<ProprietarioEntity, UUID> {

    Optional<ProprietarioEntity> findByEmail(String email);
}

