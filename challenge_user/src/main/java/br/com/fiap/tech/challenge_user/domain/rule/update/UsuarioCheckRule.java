package br.com.fiap.tech.challenge_user.domain.rule.update;

import br.com.fiap.tech.challenge_user.infrastructure.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.domain.model.Usuario;

public interface UsuarioCheckRule<T extends Usuario, E extends UsuarioEntity> {

    E updateUser(T domain, E entity);
}

