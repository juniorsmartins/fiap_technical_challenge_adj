package br.com.fiap.tech.challenge_user.application.port.output;

import br.com.fiap.tech.challenge_user.adapter.entity.ClienteEntity;

import java.util.Optional;
import java.util.UUID;

public interface ClienteFindByIdOutputPort {

    Optional<ClienteEntity> findById(UUID id);
}

