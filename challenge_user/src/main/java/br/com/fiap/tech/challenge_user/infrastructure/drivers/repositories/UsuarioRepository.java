package br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories;

import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.UsuarioDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioDao, UUID>, JpaSpecificationExecutor<UsuarioDao> {

    Optional<UsuarioDao> findByEmail(String email);

    Optional<UsuarioDao> findByLogin(String login);

    Optional<UsuarioDao> findByNome(String nome);
}

