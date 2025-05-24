package br.com.fiap.tech.challenge_user.application.core.utils;

import br.com.fiap.tech.challenge_user.adapter.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Usuario;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public final class DefaultUsuarioUpdateRule<T extends Usuario, E extends UsuarioEntity> implements UsuarioUpdateRule<T, E> {

    @Override
    public E updateUser(T domain, E entity) {
        BeanUtils.copyProperties(domain, entity, "usuarioId", "dataHoraCriacao", "dataHoraEdicao", "endereco");
        return entity;
    }
}

