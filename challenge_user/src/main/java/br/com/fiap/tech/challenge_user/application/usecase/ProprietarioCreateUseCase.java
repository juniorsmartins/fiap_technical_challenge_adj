package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.DaoPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.in.CreateInputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.domain.entities.Proprietario;
import br.com.fiap.tech.challenge_user.domain.rules.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProprietarioCreateUseCase extends AbstractCreateUseCase<Proprietario, ProprietarioDao>
        implements CreateInputPort<Proprietario> {

    private final List<UsuarioRulesStrategy<Proprietario>> rulesStrategy;

    public ProprietarioCreateUseCase(
            DaoPresenter<Proprietario, ProprietarioDao> daoPresenter,
            CreateOutputPort<ProprietarioDao> createOutputPort,
            List<UsuarioRulesStrategy<Proprietario>> rulesStrategy) {
        super(daoPresenter, createOutputPort);
        this.rulesStrategy = rulesStrategy;

    }

    @Override
    public Proprietario create(@NonNull final Proprietario usuario) {

        rulesStrategy.forEach(rule -> rule.executar(usuario));
        return super.create(usuario);
    }
}

