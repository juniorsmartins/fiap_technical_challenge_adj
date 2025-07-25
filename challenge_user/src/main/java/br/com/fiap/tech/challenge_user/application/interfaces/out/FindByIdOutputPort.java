package br.com.fiap.tech.challenge_user.application.interfaces.out;

import java.util.Optional;
import java.util.UUID;

public interface FindByIdOutputPort<E> {

    Optional<E> findById(UUID id);
}

