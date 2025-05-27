package br.com.fiap.tech.challenge_user.application.port.in;

public interface UsuarioCreateInputPort<T> {

    T create(T usuario);
}

