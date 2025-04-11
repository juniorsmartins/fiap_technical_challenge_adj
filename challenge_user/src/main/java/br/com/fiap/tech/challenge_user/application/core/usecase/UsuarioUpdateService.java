package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.application.core.domain.Usuario;
import br.com.fiap.tech.challenge_user.application.core.mapper.ApplicationMapper;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import br.com.fiap.tech.challenge_user.config.exceptions.http404.UsuarioNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioUpdateService implements UsuarioUpdateInputPort {

    private final UsuarioFindByIdOutputPort usuarioFindByIdOutputPort;

    private final ApplicationMapper applicationMapper;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Modifying
    @Override
    public Usuario update(@NonNull final Usuario usuario) {

        log.info("UsuarioUpdateService - entrada no service de update: {}", usuario);

        var id = usuario.getUsuarioId();

        var usuarioUpdated = usuarioFindByIdOutputPort.findById(id)
                .map(entity -> {
                    BeanUtils.copyProperties(usuario, entity, "usuarioId");
                    return entity;
                })
                .map(applicationMapper::toUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        log.info("UsuarioUpdateService - concluído serviço de update: {}", usuarioUpdated);

        return usuarioUpdated;
    }
}

