package br.com.fiap.tech.challenge_user.infrastructure.presenters;

public interface EntityMapper<T, E> {

    E toEntity(T domain);

    T toDomain(E entity);
}

