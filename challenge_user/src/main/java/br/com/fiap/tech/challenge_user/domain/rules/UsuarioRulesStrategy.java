package br.com.fiap.tech.challenge_user.domain.rules;

public interface UsuarioRulesStrategy<T> {

    void executar(T usuario);
}

