package br.com.fiap.tech.challenge_user.domain.rule;

public interface UsuarioRulesStrategy<T> {

    void executar(T usuario);
}

