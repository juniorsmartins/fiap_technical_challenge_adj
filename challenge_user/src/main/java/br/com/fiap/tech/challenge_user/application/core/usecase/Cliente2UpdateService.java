package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.adapter.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.adapter.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.adapter.mapper.AbstractUsuarioMapper;
import br.com.fiap.tech.challenge_user.application.core.domain.Cliente;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioCreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import br.com.fiap.tech.challenge_user.config.exceptions.http404.UsuarioNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Cliente2UpdateService implements UsuarioUpdateInputPort<Cliente> {

    private final UsuarioFindByIdOutputPort<ClienteEntity> findByIdOutputPort;

    private final UsuarioCreateOutputPort<ClienteEntity> createOutputPort;

    private final AbstractUsuarioMapper<?, ?, Cliente, ClienteEntity> mapper;

    @Override
    public Cliente update(@NonNull Cliente usuario) {

        var id = usuario.getUsuarioId();

        return findByIdOutputPort.findById(id)
                .map(entity -> atualizarUsuario(usuario, entity))
                .map(entity -> atualizarEndereco(usuario, entity))
                .map(createOutputPort::save)
                .map(mapper::toUsuarioOut)
                .orElseThrow(() -> {
                    log.error("Cliente2UpdateService - Usuário não encontrado por id: {}.", id);
                    return new UsuarioNotFoundException(id);
                });
    }

    private ClienteEntity atualizarUsuario(Cliente usuario, ClienteEntity entity) {
        BeanUtils.copyProperties(usuario, entity, "usuarioId", "dataHoraCriacao", "dataHoraEdicao", "endereco");
        return entity;
    }

    private ClienteEntity atualizarEndereco(Cliente usuario, ClienteEntity entity) {

        if (usuario.getEndereco() == null && entity.getEndereco() == null) {
            // Cenário 1: Requisição sem endereço, usuário sem endereço → Não fazer nada
            return entity;

        } else if (usuario.getEndereco() == null && entity.getEndereco() != null) {
            // Cenário 2: Requisição sem endereço, usuário com endereço → Remover endereço
            entity.setEndereco(null); // orphanRemoval = true remove o endereço do banco

        } else if (usuario.getEndereco() != null && entity.getEndereco() == null) {
            // Cenário 3: Requisição com endereço, usuário sem endereço → Criar novo endereço
            entity.setEndereco(EnderecoEntity.builder()
                    .cep(usuario.getEndereco().getCep())
                    .logradouro(usuario.getEndereco().getLogradouro())
                    .numero(usuario.getEndereco().getNumero())
                    .build()
            );

        } else if (usuario.getEndereco() != null && entity.getEndereco() != null) {
            // Cenário 4: Requisição com endereço, usuário com endereço → Atualizar endereço
            BeanUtils.copyProperties(usuario.getEndereco(), entity.getEndereco(), "enderecoId");
        }

        return entity;
    }
}
