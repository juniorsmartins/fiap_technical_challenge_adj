package br.com.fiap.tech.challenge_user.application.port.output;

import br.com.fiap.tech.challenge_user.adapter.entity.ProprietarioEntity;

import java.util.Optional;
import java.util.UUID;

public interface ProprietarioFindByIdOutputPort {

    Optional<ProprietarioEntity> findById(UUID id);
}

