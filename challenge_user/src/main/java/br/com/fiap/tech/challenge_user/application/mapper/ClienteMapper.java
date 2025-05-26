package br.com.fiap.tech.challenge_user.application.mapper;

import br.com.fiap.tech.challenge_user.adapter.dto.request.ClienteDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.request.ClienteUpdateDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.response.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class ClienteMapper implements InputMapper<ClienteDtoRequest, ClienteUpdateDtoRequest, Cliente>,
        EntityMapper<Cliente, ClienteEntity>, OutputMapper<Cliente, ClienteDtoResponse, ClienteEntity> {

    private final EnderecoMapper mapper;

    @Override
    public Cliente toDomainIn(ClienteDtoRequest dto) {
        if (dto == null) {
            return null;
        }

        var endereco = mapper.toEndereco(dto.endereco());

        return new Cliente(
                dto.nome(), dto.email(), dto.login(), dto.senha(),
                endereco, dto.numeroCartaoFidelidade()
        );
    }

    @Override
    public Cliente toDomainUpdate(ClienteUpdateDtoRequest dto) {
        if (dto == null) {
            return null;
        }

        var endereco = mapper.toEndereco(dto.endereco());

        return new Cliente(
                dto.usuarioId(), dto.nome(), dto.email(), dto.login(), dto.senha(),
                endereco, dto.numeroCartaoFidelidade()
        );
    }

    @Override
    public ClienteEntity toEntity(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        var endereco = mapper.toEnderecoEntity(cliente.getEndereco());

        return new ClienteEntity(
                cliente.getUsuarioId(), cliente.getNome(), cliente.getEmail(), cliente.getLogin(), cliente.getSenha(),
                endereco, cliente.getNumeroCartaoFidelidade(), null, null
        );
    }

    @Override
    public Cliente toDomain(ClienteEntity entity) {
        if (entity == null) {
            return null;
        }

        var endereco = mapper.toEndereco(entity.getEndereco());

        return new Cliente(
                entity.getUsuarioId(), entity.getNome(), entity.getEmail(), entity.getLogin(), entity.getSenha(),
                endereco, entity.getNumeroCartaoFidelidade()
        );
    }

    @Override
    public ClienteDtoResponse toDtoResponse(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        var endereco = mapper.toEnderecoDtoResponse(cliente.getEndereco());

        return new ClienteDtoResponse(
                cliente.getUsuarioId(), cliente.getNome(), cliente.getEmail(), cliente.getLogin(), cliente.getSenha(),
                null, null, endereco, cliente.getNumeroCartaoFidelidade()
        );
    }

    @Override
    public ClienteDtoResponse toResponse(ClienteEntity entity) {
        if (entity == null) {
            return null;
        }

        var endereco = mapper.toEnderecoDtoResponse(entity.getEndereco());

        return new ClienteDtoResponse(
                entity.getUsuarioId(), entity.getNome(), entity.getEmail(), entity.getLogin(), entity.getSenha(),
                entity.getDataHoraCriacao(), entity.getDataHoraEdicao(), endereco,
                entity.getNumeroCartaoFidelidade()
        );
    }
}

