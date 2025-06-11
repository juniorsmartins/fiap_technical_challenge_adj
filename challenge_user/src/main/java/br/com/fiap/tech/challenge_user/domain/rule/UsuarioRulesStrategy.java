package br.com.fiap.tech.challenge_user.domain.rule;

public interface UsuarioRulesStrategy<T> {

    T executar(T usuario);
}

