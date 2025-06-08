package br.com.fiap.tech.challenge_user.application.domain.rule;

public interface UsuarioRulesStrategy<T> {

    T executar(T usuario);
}

