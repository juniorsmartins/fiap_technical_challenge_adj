package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.domain.exception.http500.UsuarioNonPersistenceException;
import br.com.fiap.tech.challenge_user.infrastructure.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.infrastructure.repository.UsuarioRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class UsuarioCreateAdapter<E extends UsuarioEntity> implements CreateOutputPort<E> {

    private final UsuarioRepository repository;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Modifying
    @Override
    public E save(@NonNull final E entity) {

        try {
            return repository.saveAndFlush(entity);

        } catch (DataIntegrityViolationException e) {
            throw new UsuarioNonPersistenceException(e.getMessage());
        }
    }
}

