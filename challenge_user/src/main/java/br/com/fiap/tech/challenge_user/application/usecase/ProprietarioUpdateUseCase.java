package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.EntityMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.in.UpdateInputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.entities.Proprietario;
import br.com.fiap.tech.challenge_user.domain.rules.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.domain.rules.update.EnderecoUpdateRule;
import br.com.fiap.tech.challenge_user.domain.rules.update.UsuarioUpdateRule;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public final class ProprietarioUpdateUseCase extends AbstractUpdateUseCase<Proprietario, ProprietarioDao>
        implements UpdateInputPort<Proprietario> {

    private final List<UsuarioRulesStrategy<Proprietario>> rulesStrategy;

    private final UsuarioUpdateRule<Proprietario, ProprietarioDao> usuarioUpdateRule;

    private final EnderecoUpdateRule<Proprietario, ProprietarioDao> enderecoUpdateRule;

    public ProprietarioUpdateUseCase(
            EntityMapper<Proprietario, ProprietarioDao> entityMapper,
            CreateOutputPort<ProprietarioDao> createOutputPort,
            FindByIdOutputPort<ProprietarioDao> findByIdOutputPort,
            UsuarioUpdateRule<Proprietario, ProprietarioDao> usuarioUpdateRule,
            EnderecoUpdateRule<Proprietario, ProprietarioDao> enderecoUpdateRule,
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
    public ProprietarioDao rulesUpdate(UUID id, Proprietario domain, ProprietarioDao entity) {

        domain.setUsuarioId(id);
        rulesStrategy.forEach(rule -> rule.executar(domain));
        usuarioUpdateRule.updateUser(domain, entity);
        enderecoUpdateRule.updateAddress(domain, entity);
        return entity;
    }
}

