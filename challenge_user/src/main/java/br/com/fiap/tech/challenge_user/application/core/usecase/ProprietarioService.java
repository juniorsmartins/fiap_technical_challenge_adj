package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.adapter.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.adapter.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.core.domain.Proprietario;
import br.com.fiap.tech.challenge_user.application.core.usecase.regras.EnderecoUpdateRule;
import br.com.fiap.tech.challenge_user.application.core.usecase.regras.UsuarioUpdateRule;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioCreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioDeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioCreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioDeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProprietarioService extends AbstractUsuarioService<Proprietario, ProprietarioEntity>
        implements UsuarioCreateInputPort<Proprietario>, UsuarioUpdateInputPort<Proprietario>, UsuarioDeleteByIdInputPort<Proprietario> {

    public ProprietarioService(
            EntityMapper<Proprietario, ProprietarioEntity> entityMapper,
            UsuarioCreateOutputPort<ProprietarioEntity> createOutputPort,
            UsuarioFindByIdOutputPort<ProprietarioEntity> findByIdOutputPort,
            UsuarioDeleteOutputPort<ProprietarioEntity> deleteOutputPort,
            UsuarioUpdateRule<Proprietario, ProprietarioEntity> usuarioUpdateRule,
            EnderecoUpdateRule<Proprietario, ProprietarioEntity> enderecoUpdateRule) {
        super(entityMapper, createOutputPort, findByIdOutputPort, deleteOutputPort, usuarioUpdateRule, enderecoUpdateRule);
    }

    @Override
    public Proprietario create(@NonNull final Proprietario usuario) {
        return super.create(usuario);
    }

    @Override
    public Proprietario update(@NonNull Proprietario usuario) {
        return super.update(usuario);
    }

    @Override
    public void deleteById(@NonNull final UUID id) {
        super.deleteById(id);
    }
}

