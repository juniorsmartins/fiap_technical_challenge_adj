package br.com.fiap.tech.challenge_user.application.port.out;

public interface UsuarioCreateOutputPort<E> {

    E save(E entity);
}

