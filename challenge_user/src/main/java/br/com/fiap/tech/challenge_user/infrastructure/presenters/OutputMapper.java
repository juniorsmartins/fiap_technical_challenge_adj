package br.com.fiap.tech.challenge_user.infrastructure.presenters;

public interface OutputMapper<T, O, E> {

    O toDtoResponse(T domain);

    O toResponse(E entity);
}

