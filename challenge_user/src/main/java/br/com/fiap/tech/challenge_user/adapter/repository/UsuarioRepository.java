package br.com.fiap.tech.challenge_user.adapter.repository;

import br.com.fiap.tech.challenge_user.adapter.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
}

