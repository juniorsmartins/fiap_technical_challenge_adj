package br.com.fiap.tech.challenge_user.application.mapper;

public interface InputMapper<I, T> {

    T toDomainIn(I dto);
}

