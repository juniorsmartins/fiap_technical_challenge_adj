package br.com.fiap.tech.challenge_user.infrastructure.adapters.gateways;

import br.com.fiap.tech.challenge_user.application.interfaces.out.UsuarioFindByLoginOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.UsuarioDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.UsuarioRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UsuarioFindByLoginGateway implements UsuarioFindByLoginOutputPort {

    private final UsuarioRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Optional<UsuarioDao> findByLogin(@NonNull final String login) {

        return repository.findByLogin(login);
    }
}

