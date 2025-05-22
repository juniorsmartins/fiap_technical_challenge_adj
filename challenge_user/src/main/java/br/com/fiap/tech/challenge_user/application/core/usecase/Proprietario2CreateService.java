package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.adapter.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.adapter.mapper.AbstractUsuarioMapper;
import br.com.fiap.tech.challenge_user.application.core.domain.Proprietario;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioCreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioCreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioDeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class Proprietario2CreateService extends AbstractUsuarioService<Proprietario, ProprietarioEntity>
        implements UsuarioCreateInputPort<Proprietario> {

    public Proprietario2CreateService(
            AbstractUsuarioMapper<?, ?, Proprietario, ProprietarioEntity> mapper,
            UsuarioCreateOutputPort<ProprietarioEntity> createOutputPort,
            UsuarioFindByIdOutputPort<ProprietarioEntity> findByIdOutputPort,
            UsuarioDeleteOutputPort<ProprietarioEntity> deleteOutputPort) {
        super(mapper, createOutputPort, findByIdOutputPort, deleteOutputPort);
    }

    @Override
    public Proprietario create(@NonNull final Proprietario usuario) {
        return super.create(usuario);
    }
}

