package br.com.fiap.tech.challenge_user.application.interfaces.out;

import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.UsuarioDao;

import java.util.Optional;

public interface UsuarioFindByNomeOutputPort {

    Optional<UsuarioDao> findByNome(String nome);
}

