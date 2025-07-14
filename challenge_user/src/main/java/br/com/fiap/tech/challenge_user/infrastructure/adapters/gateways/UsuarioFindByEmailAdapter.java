package br.com.fiap.tech.challenge_user.infrastructure.adapters.gateways;

import br.com.fiap.tech.challenge_user.application.interfaces.out.UsuarioFindByEmailOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.UsuarioEntity;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.UsuarioRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UsuarioFindByEmailAdapter implements UsuarioFindByEmailOutputPort {

    private final UsuarioRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Optional<UsuarioEntity> findByEmail(@NonNull final String email) {

        return repository.findByEmail(email);
    }
}

