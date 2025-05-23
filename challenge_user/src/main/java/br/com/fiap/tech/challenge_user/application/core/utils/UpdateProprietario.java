package br.com.fiap.tech.challenge_user.application.core.utils;

import br.com.fiap.tech.challenge_user.adapter.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Proprietario;
import org.springframework.stereotype.Service;

@Service
public class UpdateProprietario extends AbstractUpdateUser<Proprietario, ProprietarioEntity>
        implements UsuarioUpdateUtils<Proprietario, ProprietarioEntity> {

    @Override
    public ProprietarioEntity up(Proprietario usuario, ProprietarioEntity entity) {
        return super.upUsuario(usuario, entity);
    }
}

