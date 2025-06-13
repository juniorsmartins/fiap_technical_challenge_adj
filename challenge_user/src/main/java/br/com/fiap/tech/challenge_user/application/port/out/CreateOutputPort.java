package br.com.fiap.tech.challenge_user.application.port.out;

public interface CreateOutputPort<E> {

    E save(E entity);
}

