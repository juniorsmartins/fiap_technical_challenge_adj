package br.com.fiap.tech.challenge_user.adapter.mapper;

public interface EntityMapper<T, E> {

    E toEntity(T domain);

    T toDomain(E entity);
}

