package br.com.fiap.tech.challenge_user.domain.rules.update;

import br.com.fiap.tech.challenge_user.domain.entities.Usuario;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.UsuarioDao;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public final class UsuarioUpdateRuleImpl<T extends Usuario, E extends UsuarioDao> implements UsuarioUpdateRule<T, E> {

    @Override
    public E updateUser(T domain, E entity) {
        BeanUtils.copyProperties(domain, entity, "usuarioId", "dataHoraCriacao", "dataHoraEdicao", "endereco");
        return entity;
    }
}

