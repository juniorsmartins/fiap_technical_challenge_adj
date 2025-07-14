package br.com.fiap.tech.challenge_user.infrastructure.adapters.gateways;

import br.com.fiap.tech.challenge_user.application.interfaces.out.UsuarioFindByNomeOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.UsuarioEntity;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.UsuarioRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UsuarioFindByNomeAdapter implements UsuarioFindByNomeOutputPort {

    private final UsuarioRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Optional<UsuarioEntity> findByNome(@NonNull final String nome) {

        return repository.findByNome(nome);
    }
}

