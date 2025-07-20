package br.com.fiap.tech.challenge_user.domain.rules.update;

import br.com.fiap.tech.challenge_user.domain.models.Usuario;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.UsuarioDao;

public interface EnderecoUpdateRule<T extends Usuario, E extends UsuarioDao> {

    E updateAddress(T domain, E entity);
}

