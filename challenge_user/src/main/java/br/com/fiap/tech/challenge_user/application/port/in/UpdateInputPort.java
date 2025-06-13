package br.com.fiap.tech.challenge_user.application.port.in;

import java.util.UUID;

public interface UpdateInputPort<T> {

    T update(UUID id, T usuario);
}

