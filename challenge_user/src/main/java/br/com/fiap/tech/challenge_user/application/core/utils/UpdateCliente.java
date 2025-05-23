package br.com.fiap.tech.challenge_user.application.core.utils;

import br.com.fiap.tech.challenge_user.adapter.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Cliente;
import org.springframework.stereotype.Service;

@Service
public class UpdateCliente extends AbstractUpdateUser<Cliente, ClienteEntity>
        implements UsuarioUpdateUtils<Cliente, ClienteEntity> {

    @Override
    public ClienteEntity up(Cliente usuario, ClienteEntity entity) {
        return super.upUsuario(usuario, entity);
    }
}

