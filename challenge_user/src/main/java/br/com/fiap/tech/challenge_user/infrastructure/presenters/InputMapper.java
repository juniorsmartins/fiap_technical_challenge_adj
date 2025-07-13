package br.com.fiap.tech.challenge_user.infrastructure.presenters;

public interface InputMapper<I, T> {

    T toDomainIn(I dto);
}

