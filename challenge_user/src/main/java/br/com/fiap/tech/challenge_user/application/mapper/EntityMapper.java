package br.com.fiap.tech.challenge_user.application.mapper;

public interface EntityMapper<T, E> {

    E toEntity(T domain);

    T toDomain(E entity);
}

