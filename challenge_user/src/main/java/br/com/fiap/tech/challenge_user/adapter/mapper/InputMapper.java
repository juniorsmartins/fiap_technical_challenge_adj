package br.com.fiap.tech.challenge_user.adapter.mapper;

public interface InputMapper<I, U, T> {

    T toDomainIn(I dto);

    T toDomainUpdate(U dto);
}

