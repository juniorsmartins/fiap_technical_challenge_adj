package br.com.fiap.tech.challenge_user.application.interfaces.in;

public interface CreateInputPort<T> {

    T create(T domain);
}

