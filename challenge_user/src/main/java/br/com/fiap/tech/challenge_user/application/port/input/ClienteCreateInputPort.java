package br.com.fiap.tech.challenge_user.application.port.input;

import br.com.fiap.tech.challenge_user.application.core.domain.Cliente;

public interface ClienteCreateInputPort {

    Cliente create(Cliente cliente);
}

