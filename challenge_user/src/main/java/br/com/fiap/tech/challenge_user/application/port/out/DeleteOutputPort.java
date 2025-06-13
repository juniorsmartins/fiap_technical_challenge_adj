package br.com.fiap.tech.challenge_user.application.port.out;

public interface DeleteOutputPort<E> {

    void delete(E entity);
}

