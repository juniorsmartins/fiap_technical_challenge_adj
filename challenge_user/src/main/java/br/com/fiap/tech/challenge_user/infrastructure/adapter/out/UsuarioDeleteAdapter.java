package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.port.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.infrastructure.repository.UsuarioRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class UsuarioDeleteAdapter<E extends UsuarioEntity> implements DeleteOutputPort<E> {

    private final UsuarioRepository repository;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Override
    public void delete(@NonNull final E usuario) {
        repository.delete(usuario);
    }
}

