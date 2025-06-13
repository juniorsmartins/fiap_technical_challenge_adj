package br.com.fiap.tech.challenge_user.application.port.in;

import java.util.UUID;

public interface DeleteByIdInputPort<T> {

    void deleteById(UUID id);
}

