package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.adapter.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.adapter.mapper.AbstractUsuarioMapper;
import br.com.fiap.tech.challenge_user.application.core.domain.Proprietario;
import br.com.fiap.tech.challenge_user.application.core.utils.UpdateUserRule;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioCreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioDeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ProprietarioUpdateService extends AbstractUsuarioService<Proprietario, ProprietarioEntity>
        implements UsuarioUpdateInputPort<Proprietario> {

    public ProprietarioUpdateService(
            AbstractUsuarioMapper<?, ?, ?, Proprietario, ProprietarioEntity> mapper,
            UsuarioCreateOutputPort<ProprietarioEntity> createOutputPort,
            UsuarioFindByIdOutputPort<ProprietarioEntity> findByIdOutputPort,
            UsuarioDeleteOutputPort<ProprietarioEntity> deleteOutputPort,
            UpdateUserRule<Proprietario, ProprietarioEntity> updateUserRule) {
        super(mapper, createOutputPort, findByIdOutputPort, deleteOutputPort, updateUserRule);
    }

    @Override
    public Proprietario update(@NonNull Proprietario usuario) {
        return super.update(usuario);
    }
}

