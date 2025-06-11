package br.com.fiap.tech.challenge_user.domain.rule.update;

import br.com.fiap.tech.challenge_user.domain.model.Usuario;
import br.com.fiap.tech.challenge_user.infrastructure.entity.UsuarioEntity;
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

