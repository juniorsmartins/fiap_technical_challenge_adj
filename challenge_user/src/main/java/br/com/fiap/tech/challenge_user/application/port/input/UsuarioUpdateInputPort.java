package br.com.fiap.tech.challenge_user.application.port.input;

import java.util.UUID;

public interface UsuarioUpdateInputPort<T> {

    T update(UUID id, T usuario);
}

