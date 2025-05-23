package br.com.fiap.tech.challenge_user.adapter.repository;

import br.com.fiap.tech.challenge_user.adapter.entity.AbstractUsuarioEntity;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UsuarioFindByIdAdapter<E extends AbstractUsuarioEntity> implements UsuarioFindByIdOutputPort<E> {

    private final UsuarioRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Optional<E> findById(@NonNull final UUID id) {
        return  repository.findById(id)
                .map(entity -> (E) entity);
    }
}

