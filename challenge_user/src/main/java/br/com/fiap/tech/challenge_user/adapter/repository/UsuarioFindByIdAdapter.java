package br.com.fiap.tech.challenge_user.adapter.repository;

import br.com.fiap.tech.challenge_user.adapter.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UsuarioFindByIdAdapter implements UsuarioFindByIdOutputPort {

    private final UsuarioRepository usuarioRepository;

    @Override
    public Optional<UsuarioEntity> findById(@NonNull final UUID id) {
        return usuarioRepository.findById(id);
    }
}

