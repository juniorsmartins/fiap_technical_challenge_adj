package br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters;

public interface DaoPresenter<T, E> {

    E toEntity(T domain);

    T toDomain(E entity);
}

