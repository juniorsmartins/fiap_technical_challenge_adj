package br.com.fiap.tech.challenge_user.infrastructure.repository;

import br.com.fiap.tech.challenge_user.infrastructure.entity.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, UUID> {

}

