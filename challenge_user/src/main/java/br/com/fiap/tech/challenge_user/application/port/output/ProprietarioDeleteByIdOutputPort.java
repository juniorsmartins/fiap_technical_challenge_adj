package br.com.fiap.tech.challenge_user.application.port.output;

import java.util.UUID;

public interface ProprietarioDeleteByIdOutputPort {

    void deleteById(UUID id);
}

