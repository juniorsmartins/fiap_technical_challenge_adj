package br.com.fiap.tech.challenge_user.application.core.usecase.regras;

import br.com.fiap.tech.challenge_user.adapter.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Usuario;

public interface UsuarioUpdateRule<T extends Usuario, E extends UsuarioEntity> {

    E updateUser(T domain, E entity);
}

