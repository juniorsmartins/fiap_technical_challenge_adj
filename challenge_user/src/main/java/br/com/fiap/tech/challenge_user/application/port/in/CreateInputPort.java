package br.com.fiap.tech.challenge_user.application.port.in;

public interface CreateInputPort<T> {

    T create(T domain);
}

