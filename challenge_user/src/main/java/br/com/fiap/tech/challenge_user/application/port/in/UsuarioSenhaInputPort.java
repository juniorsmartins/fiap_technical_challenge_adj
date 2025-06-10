package br.com.fiap.tech.challenge_user.application.port.in;

import java.util.UUID;

public interface UsuarioSenhaInputPort<E> {

    void updatePassword(UUID usuarioId, String senhaAntiga, String senhaNova);
}

