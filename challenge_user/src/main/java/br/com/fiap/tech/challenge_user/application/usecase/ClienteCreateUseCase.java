package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.DaoPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.in.CreateInputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.domain.entities.Cliente;
import br.com.fiap.tech.challenge_user.domain.rules.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ClienteDao;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteCreateUseCase extends AbstractCreateUseCase<Cliente, ClienteDao>
        implements CreateInputPort<Cliente> {

    private final List<UsuarioRulesStrategy<Cliente>> rulesStrategy;

    public ClienteCreateUseCase(
            DaoPresenter<Cliente, ClienteDao> daoPresenter,
            CreateOutputPort<ClienteDao> createOutputPort,
            List<UsuarioRulesStrategy<Cliente>> rulesStrategy) {
        super(daoPresenter, createOutputPort);
        this.rulesStrategy = rulesStrategy;
    }

    @Override
    public Cliente create(@NonNull final Cliente usuario) {

        rulesStrategy.forEach(rule -> rule.executar(usuario));
        return super.create(usuario);
    }
}

