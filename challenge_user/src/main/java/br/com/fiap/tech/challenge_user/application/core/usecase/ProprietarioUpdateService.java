package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.adapter.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Proprietario;
import br.com.fiap.tech.challenge_user.application.core.usecase.regras.EnderecoUpdateRule;
import br.com.fiap.tech.challenge_user.application.core.usecase.regras.UsuarioUpdateRule;
import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioCreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ProprietarioUpdateService extends AbstractUsuarioUpdateService<Proprietario, ProprietarioEntity>
        implements UsuarioUpdateInputPort<Proprietario> {

    public ProprietarioUpdateService(
            EntityMapper<Proprietario, ProprietarioEntity> entityMapper,
            UsuarioCreateOutputPort<ProprietarioEntity> createOutputPort,
            UsuarioFindByIdOutputPort<ProprietarioEntity> findByIdOutputPort,
            UsuarioUpdateRule<Proprietario, ProprietarioEntity> usuarioUpdateRule,
            EnderecoUpdateRule<Proprietario, ProprietarioEntity> enderecoUpdateRule) {
        super(entityMapper, createOutputPort, findByIdOutputPort, usuarioUpdateRule, enderecoUpdateRule);
    }

    @Override
    public Proprietario update(@NonNull Proprietario usuario) {
        return super.update(usuario);
    }
}

