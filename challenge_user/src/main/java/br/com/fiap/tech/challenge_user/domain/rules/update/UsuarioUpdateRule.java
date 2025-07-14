package br.com.fiap.tech.challenge_user.domain.rules.update;

import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.UsuarioEntity;
import br.com.fiap.tech.challenge_user.domain.models.Usuario;

public interface UsuarioUpdateRule<T extends Usuario, E extends UsuarioEntity> {

    E updateUser(T domain, E entity);
}

