package br.com.fiap.tech.challenge_user.infrastructure.adapters.gateways;

import br.com.fiap.tech.challenge_user.application.interfaces.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.UsuarioDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.UsuarioRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class UsuarioDeleteAdapter<E extends UsuarioDao> implements DeleteOutputPort<E> {

    private final UsuarioRepository repository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Override
    public void delete(@NonNull final E usuario) {

        repository.delete(usuario);
    }
}

