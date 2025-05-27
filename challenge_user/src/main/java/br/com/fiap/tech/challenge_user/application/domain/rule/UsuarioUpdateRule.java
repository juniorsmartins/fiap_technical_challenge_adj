package br.com.fiap.tech.challenge_user.application.domain.rule;

import br.com.fiap.tech.challenge_user.infrastructure.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.application.domain.model.Usuario;

public interface UsuarioUpdateRule<T extends Usuario, E extends UsuarioEntity> {

    E updateUser(T domain, E entity);
}

