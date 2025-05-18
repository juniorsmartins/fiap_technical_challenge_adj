package br.com.fiap.tech.challenge_user.application.port.output;

import java.util.UUID;

public interface ClienteDeleteByIdOutputPort {

    void deleteById(UUID id);
}

