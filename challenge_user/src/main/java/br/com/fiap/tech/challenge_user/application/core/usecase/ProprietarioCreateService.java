package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.adapter.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Proprietario;
import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioCreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioCreateOutputPort;
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

