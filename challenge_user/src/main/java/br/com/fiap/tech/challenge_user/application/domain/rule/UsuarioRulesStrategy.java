package br.com.fiap.tech.challenge_user.application.domain.rule;

import br.com.fiap.tech.challenge_user.application.domain.model.Usuario;

public interface UsuarioRulesStrategy {

    <T extends Usuario> T executar(T usuario);
}

