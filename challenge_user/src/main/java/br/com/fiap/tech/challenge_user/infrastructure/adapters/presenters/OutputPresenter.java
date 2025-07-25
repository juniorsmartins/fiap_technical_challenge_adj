package br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters;

public interface OutputPresenter<T, O, E> {

    O toDtoResponse(T domain);

    O toResponse(E entity);
}

