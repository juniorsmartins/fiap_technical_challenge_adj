package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.domain.model.Proprietario;
import br.com.fiap.tech.challenge_user.domain.rule.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.domain.rule.update.EnderecoUpdateRule;
import br.com.fiap.tech.challenge_user.domain.rule.update.UsuarioUpdateRule;
import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.in.UsuarioUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProprietarioUpdateService extends AbstractUsuarioUpdateService<Proprietario, ProprietarioEntity>
        implements UsuarioUpdateInputPort<Proprietario> {

    public ProprietarioUpdateService(
            EntityMapper<Proprietario, ProprietarioEntity> entityMapper,
            CreateOutputPort<ProprietarioEntity> createOutputPort,
            FindByIdOutputPort<ProprietarioEntity> findByIdOutputPort,
            UsuarioUpdateRule<Proprietario, ProprietarioEntity> usuarioUpdateRule,
            EnderecoUpdateRule<Proprietario, ProprietarioEntity> enderecoUpdateRule,
            List<UsuarioRulesStrategy<Proprietario>> rulesStrategies) {
        super(entityMapper, createOutputPort, findByIdOutputPort, usuarioUpdateRule, enderecoUpdateRule, rulesStrategies);
    }

    @Override
    public Proprietario update(@NonNull UUID id, @NonNull Proprietario usuario) {
        return super.update(id, usuario);
    }
}

