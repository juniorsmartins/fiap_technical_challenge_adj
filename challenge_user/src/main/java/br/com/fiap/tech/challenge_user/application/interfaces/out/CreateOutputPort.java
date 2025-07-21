package br.com.fiap.tech.challenge_user.application.interfaces.out;

public interface CreateOutputPort<E> {

    E save(E entity);
}

