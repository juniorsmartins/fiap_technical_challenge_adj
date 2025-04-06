package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.application.core.domain.Usuario;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioCreateInputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioCreateService implements UsuarioCreateInputPort {

    @Override
    public Usuario create(Usuario usuario) {
        usuario.setUsuarioId(UUID.randomUUID());
        return usuario;
    }
}

