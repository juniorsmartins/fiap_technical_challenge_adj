package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.application.domain.model.Proprietario;
import br.com.fiap.tech.challenge_user.application.domain.rule.update.EnderecoUpdateRule;
import br.com.fiap.tech.challenge_user.application.domain.rule.update.UsuarioUpdateRule;
import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.in.UsuarioUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioCreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioFindByIdOutputPort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
    public Proprietario update(@NonNull UUID id, @NonNull Proprietario usuario) {
        return super.update(id, usuario);
    }
}

