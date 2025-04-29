package br.com.fiap.tech.challenge_user.adapter.repository;

import br.com.fiap.tech.challenge_user.adapter.entity.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, UUID> {

}

