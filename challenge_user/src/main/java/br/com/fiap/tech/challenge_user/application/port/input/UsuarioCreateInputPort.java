package br.com.fiap.tech.challenge_user.application.port.input;

import br.com.fiap.tech.challenge_user.application.core.domain.Usuario;

public interface UsuarioCreateInputPort {

    Usuario create(Usuario usuario);
}

