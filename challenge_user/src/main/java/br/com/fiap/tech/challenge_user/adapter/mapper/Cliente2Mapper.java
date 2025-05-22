package br.com.fiap.tech.challenge_user.adapter.mapper;

import br.com.fiap.tech.challenge_user.adapter.dto.request.ClienteDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.response.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Cliente2Mapper
        implements AbstractUsuarioMapper<ClienteDtoRequest, ClienteDtoResponse, Cliente, ClienteEntity> {

    private final Endereco2Mapper mapper;

    public Cliente toUsuarioIn(ClienteDtoRequest dto) {
        if (dto == null) {
            return null;
        }

        var cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setLogin(dto.login());
        cliente.setSenha(dto.senha());
        cliente.setEndereco(mapper.toEnderecoIn(dto.endereco()));
        cliente.setNumeroCartaoFidelidade(dto.numeroCartaoFidelidade());

        return cliente;
    }

    public Cliente toUsuarioOut(ClienteEntity entity) {
        if (entity == null) {
            return null;
        }

        var cliente = new Cliente();
        cliente.setUsuarioId(entity.getUsuarioId());
        cliente.setNome(entity.getNome());
        cliente.setEmail(entity.getEmail());
        cliente.setLogin(entity.getLogin());
        cliente.setSenha(entity.getSenha());
        cliente.setEndereco(mapper.toEnderecoOut(entity.getEndereco()));
        cliente.setNumeroCartaoFidelidade(entity.getNumeroCartaoFidelidade());

        return cliente;
    }

    public ClienteEntity toUsuarioEntity(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        var entity = new ClienteEntity();
        entity.setUsuarioId(cliente.getUsuarioId());
        entity.setNome(cliente.getNome());
        entity.setEmail(cliente.getEmail());
        entity.setLogin(cliente.getLogin());
        entity.setSenha(cliente.getSenha());
        entity.setEndereco(mapper.toEnderecoEntity(cliente.getEndereco()));
        entity.setNumeroCartaoFidelidade(cliente.getNumeroCartaoFidelidade());

        return entity;
    }

    public ClienteDtoResponse toDtoResponse(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        return new ClienteDtoResponse(
                cliente.getUsuarioId(), cliente.getNome(), cliente.getEmail(), cliente.getLogin(), cliente.getSenha(),
                null, null, mapper.toEnderecoDtoResponse(cliente.getEndereco()),
                cliente.getNumeroCartaoFidelidade()
        );
    }

    public ClienteDtoResponse toUsuarioDtoResponse(ClienteEntity entity) {
        if (entity == null) {
            return null;
        }

        return new ClienteDtoResponse(
                entity.getUsuarioId(), entity.getNome(), entity.getEmail(), entity.getLogin(), entity.getSenha(),
                entity.getDataHoraCriacao(), entity.getDataHoraEdicao(), mapper.toEnderecoDtoResponse(entity.getEndereco()),
                entity.getNumeroCartaoFidelidade()
        );
    }
}

