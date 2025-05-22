package br.com.fiap.tech.challenge_user.application.port.input;

public interface UsuarioCreateInputPort<T> {

    T create(T usuario);
}

