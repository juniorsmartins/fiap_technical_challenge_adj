package br.com.fiap.tech.challenge_user.domain.rules.update;

import br.com.fiap.tech.challenge_user.domain.models.Usuario;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.UsuarioEntity;

public interface EnderecoUpdateRule<T extends Usuario, E extends UsuarioEntity> {

    E updateAddress(T domain, E entity);
}

