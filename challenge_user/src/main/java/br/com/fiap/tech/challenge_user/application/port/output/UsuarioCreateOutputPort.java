package br.com.fiap.tech.challenge_user.application.port.output;

public interface UsuarioCreateOutputPort<E> {

    E save(E entity);
}

