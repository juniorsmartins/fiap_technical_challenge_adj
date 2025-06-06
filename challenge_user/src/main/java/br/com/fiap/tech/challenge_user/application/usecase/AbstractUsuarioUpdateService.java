package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.application.domain.model.Usuario;
import br.com.fiap.tech.challenge_user.application.domain.rule.update.EnderecoUpdateRule;
import br.com.fiap.tech.challenge_user.application.domain.rule.update.UsuarioUpdateRule;
import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioCreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioFindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.domain.exception.http404.UsuarioNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class AbstractUsuarioUpdateService<T extends Usuario, E extends UsuarioEntity> {

    private final EntityMapper<T, E> entityMapper;

    private final UsuarioCreateOutputPort<E> createOutputPort;

    private final UsuarioFindByIdOutputPort<E> findByIdOutputPort;

    private final UsuarioUpdateRule<T, E> usuarioUpdateRule;

    private final EnderecoUpdateRule<T, E> enderecoUpdateRule;

    public T update(@NonNull UUID id, @NonNull T usuario) {

        return findByIdOutputPort.findById(id)
                .map(entity -> usuarioUpdateRule.updateUser(usuario, entity))
                .map(entity -> enderecoUpdateRule.updateAddress(usuario, entity))
                .map(createOutputPort::save)
                .map(entityMapper::toDomain)
                .orElseThrow(() -> {
                    log.error("AbstractUsuarioUpdateService - Usuário não encontrado por id: {}.", id);
                    return new UsuarioNotFoundException(id);
                });
    }
}

