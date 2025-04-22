package br.com.fiap.tech.challenge_user.adapter.repository;

import br.com.fiap.tech.challenge_user.application.port.output.UsuarioDeleteByIdOutputPort;
import br.com.fiap.tech.challenge_user.config.exceptions.http404.UsuarioNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UsuarioDeleteAdapter implements UsuarioDeleteByIdOutputPort {

    private final UsuarioRepository usuarioRepository;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    @Modifying
    @Override
    public void deleteById(@NonNull final UUID id) {
        usuarioRepository.findById(id)
                .ifPresentOrElse(usuarioRepository::delete,
                        () -> {throw new UsuarioNotFoundException(id);}
                );
    }
}

