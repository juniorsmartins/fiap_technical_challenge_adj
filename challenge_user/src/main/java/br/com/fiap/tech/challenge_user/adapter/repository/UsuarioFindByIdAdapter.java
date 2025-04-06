package br.com.fiap.tech.challenge_user.adapter.repository;

import br.com.fiap.tech.challenge_user.adapter.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import br.com.fiap.tech.challenge_user.config.exceptions.http404.UsuarioNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UsuarioFindByIdAdapter implements UsuarioFindByIdOutputPort {

    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public UsuarioEntity findById(@NonNull final UUID id) {

        log.info("UsuarioFindByIdAdapter - iniciada consulta por findById: {}", id);

        var usuarioFind = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        log.info("UsuarioFindByIdAdapter - concluída consulta por findById: {}", usuarioFind);

        return usuarioFind;
    }
}

