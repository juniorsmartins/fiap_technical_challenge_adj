package br.com.fiap.tech.challenge_user.application.port.output;

import br.com.fiap.tech.challenge_user.adapter.entity.ClienteEntity;

public interface ClienteCreateOutputPort {

    ClienteEntity save(ClienteEntity clienteEntity);
}

