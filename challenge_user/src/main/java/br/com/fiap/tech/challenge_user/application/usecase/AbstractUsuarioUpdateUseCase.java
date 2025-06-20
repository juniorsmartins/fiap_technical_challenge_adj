package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.exception.http404.UsuarioNotFoundException;
import br.com.fiap.tech.challenge_user.domain.model.Usuario;
import br.com.fiap.tech.challenge_user.domain.rule.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.domain.rule.update.EnderecoUpdateRule;
import br.com.fiap.tech.challenge_user.domain.rule.update.UsuarioUpdateRule;
import br.com.fiap.tech.challenge_user.infrastructure.entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class AbstractUsuarioUpdateUseCase<T extends Usuario, E extends UsuarioEntity> {

    private final EntityMapper<T, E> entityMapper;

    private final CreateOutputPort<E> createOutputPort;

    private final FindByIdOutputPort<E> findByIdOutputPort;

    private final UsuarioUpdateRule<T, E> usuarioUpdateRule;

    private final EnderecoUpdateRule<T, E> enderecoUpdateRule;

    private final List<UsuarioRulesStrategy<T>> rulesStrategy;

    public T update(final UUID id, T usuario) {

        usuario.setUsuarioId(id);
        this.rules(usuario);

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

    private T rules(T usuario) {
        rulesStrategy.forEach(rule -> rule.executar(usuario));
        return usuario;
    }
}

