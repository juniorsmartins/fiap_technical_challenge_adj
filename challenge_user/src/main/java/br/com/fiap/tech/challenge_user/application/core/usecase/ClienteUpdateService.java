package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.adapter.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.adapter.mapper.AbstractUsuarioMapper;
import br.com.fiap.tech.challenge_user.application.core.domain.Cliente;
import br.com.fiap.tech.challenge_user.application.core.utils.UpdateUserRule;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioCreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import br.com.fiap.tech.challenge_user.config.exceptions.http404.UsuarioNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteUpdateService implements UsuarioUpdateInputPort<Cliente> {

    private final AbstractUsuarioMapper<?, ?, ?, Cliente, ClienteEntity> mapper;

    private final UsuarioFindByIdOutputPort<ClienteEntity> findByIdOutputPort;

    private final UsuarioCreateOutputPort<ClienteEntity> createOutputPort;

    private final UpdateUserRule<Cliente, ClienteEntity> updateUserRule;

    @Override
    public Cliente update(@NonNull Cliente usuario) {

        var id = usuario.getUsuarioId();

        return findByIdOutputPort.findById(id)
                .map(entity -> updateUserRule.upUsuario(usuario, entity))
                .map(entity -> updateUserRule.upEndereco(usuario, entity))
                .map(createOutputPort::save)
                .map(mapper::toUsuarioOut)
                .orElseThrow(() -> {
                    log.error("ClienteUpdateService - Usuário não encontrado por id: {}.", id);
                    return new UsuarioNotFoundException(id);
                });
    }
}

