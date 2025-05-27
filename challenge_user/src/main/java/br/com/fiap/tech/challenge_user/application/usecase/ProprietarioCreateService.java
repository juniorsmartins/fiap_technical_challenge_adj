package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.application.domain.model.Proprietario;
import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.in.UsuarioCreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioCreateOutputPort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ProprietarioCreateService extends AbstractUsuarioCreateService<Proprietario, ProprietarioEntity>
        implements UsuarioCreateInputPort<Proprietario> {

    public ProprietarioCreateService(
            EntityMapper<Proprietario, ProprietarioEntity> entityMapper,
            UsuarioCreateOutputPort<ProprietarioEntity> createOutputPort) {
        super(entityMapper, createOutputPort);
    }

    @Override
    public Proprietario create(@NonNull final Proprietario usuario) {
        return super.create(usuario);
    }
}

