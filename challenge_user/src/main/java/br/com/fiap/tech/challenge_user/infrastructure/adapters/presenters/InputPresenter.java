package br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters;

public interface InputPresenter<I, T> {

    T toDomainIn(I dto);
}

