package br.com.fiap.tech.challenge_user.application.port.input;

import br.com.fiap.tech.challenge_user.application.core.domain.Usuario;

public interface UsuarioUpdateInputPort {

    Usuario update(Usuario usuario);
}

