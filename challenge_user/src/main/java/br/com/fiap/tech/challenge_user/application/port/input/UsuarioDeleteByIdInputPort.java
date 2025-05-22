package br.com.fiap.tech.challenge_user.application.port.input;

import java.util.UUID;

public interface UsuarioDeleteByIdInputPort<T> {

    void deleteById(UUID id);
}

