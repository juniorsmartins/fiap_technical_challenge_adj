package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.infrastructure.repository.UsuarioRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UsuarioFindByIdAdapter<E extends UsuarioEntity> implements FindByIdOutputPort<E> {

    private final UsuarioRepository repository;

    @SuppressWarnings({"unchecked"})
    @Transactional(readOnly = true)
    @Override
    public Optional<E> findById(@NonNull final UUID id) {

        return repository.findById(id)
                .map(entity -> (E) entity);
    }
}

