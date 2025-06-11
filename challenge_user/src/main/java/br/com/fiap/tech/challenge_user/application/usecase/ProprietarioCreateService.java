package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.domain.rule.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.domain.model.Proprietario;
import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.in.UsuarioCreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioCreateOutputPort;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProprietarioCreateService extends AbstractUsuarioCreateService<Proprietario, ProprietarioEntity>
        implements UsuarioCreateInputPort<Proprietario> {

    public ProprietarioCreateService(
            EntityMapper<Proprietario, ProprietarioEntity> entityMapper,
            UsuarioCreateOutputPort<ProprietarioEntity> createOutputPort,
            List<UsuarioRulesStrategy<Proprietario>> rulesStrategy) {
        super(entityMapper, createOutputPort, rulesStrategy);
    }

    @Override
    public Proprietario create(@NonNull final Proprietario usuario) {
        return super.create(usuario);
    }
}

