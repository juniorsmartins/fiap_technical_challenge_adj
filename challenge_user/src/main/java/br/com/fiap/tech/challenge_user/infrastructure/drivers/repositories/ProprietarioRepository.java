package br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories;

import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProprietarioRepository extends JpaRepository<ProprietarioDao, UUID>,
        JpaSpecificationExecutor<ProprietarioDao> {

    Optional<ProprietarioDao> findByEmail(String email);
}

