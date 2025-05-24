package br.com.fiap.tech.challenge_user.application.core.utils;

import br.com.fiap.tech.challenge_user.adapter.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Usuario;

public interface UsuarioUpdateUtils<T extends Usuario, E extends UsuarioEntity> {

    E up(T usuario, E entity);
}

