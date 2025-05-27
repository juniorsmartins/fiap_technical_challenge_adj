package br.com.fiap.tech.challenge_user.application.domain.rule;

import br.com.fiap.tech.challenge_user.application.domain.model.Usuario;
import br.com.fiap.tech.challenge_user.infrastructure.entity.UsuarioEntity;

public interface EnderecoUpdateRule<T extends Usuario, E extends UsuarioEntity> {

    E updateAddress(T domain, E entity);
}

