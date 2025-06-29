package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.in.UpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.model.Proprietario;
import br.com.fiap.tech.challenge_user.domain.rule.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.domain.rule.update.EnderecoUpdateRule;
import br.com.fiap.tech.challenge_user.domain.rule.update.UsuarioUpdateRule;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public final class ProprietarioUpdateUseCase extends AbstractUpdateUseCase<Proprietario, ProprietarioEntity>
        implements UpdateInputPort<Proprietario> {

    private final List<UsuarioRulesStrategy<Proprietario>> rulesStrategy;

    private final UsuarioUpdateRule<Proprietario, ProprietarioEntity> usuarioUpdateRule;

    private final EnderecoUpdateRule<Proprietario, ProprietarioEntity> enderecoUpdateRule;

    public ProprietarioUpdateUseCase(
            EntityMapper<Proprietario, ProprietarioEntity> entityMapper,
            CreateOutputPort<ProprietarioEntity> createOutputPort,
            FindByIdOutputPort<ProprietarioEntity> findByIdOutputPort,
            UsuarioUpdateRule<Proprietario, ProprietarioEntity> usuarioUpdateRule,
            EnderecoUpdateRule<Proprietario, ProprietarioEntity> enderecoUpdateRule,
            List<UsuarioRulesStrategy<Proprietario>> rulesStrategies) {
        super(entityMapper, createOutputPort, findByIdOutputPort);
        this.rulesStrategy = rulesStrategies;
        this.usuarioUpdateRule = usuarioUpdateRule;
        this.enderecoUpdateRule = enderecoUpdateRule;

    }

    @Override
    public Proprietario update(@NonNull final UUID id, @NonNull Proprietario usuario) {

        return super.update(id, usuario);
    }

    @Override
    public ProprietarioEntity rulesUpdate(UUID id, Proprietario domain, ProprietarioEntity entity) {

        domain.setUsuarioId(id);
        rulesStrategy.forEach(rule -> rule.executar(domain));
        usuarioUpdateRule.updateUser(domain, entity);
        enderecoUpdateRule.updateAddress(domain, entity);
        return entity;
    }
}

