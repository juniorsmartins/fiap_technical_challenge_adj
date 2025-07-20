package br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters;

import br.com.fiap.tech.challenge_user.domain.entities.Cliente;
import br.com.fiap.tech.challenge_user.application.dtos.in.ClienteDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ClienteDao;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public final class ClienteMapper implements InputMapper<ClienteDtoRequest, Cliente>,
        OutputMapper<Cliente, ClienteDtoResponse, ClienteDao>,
        EntityMapper<Cliente, ClienteDao>,
        PageMapper<ClienteDtoResponse, ClienteDao> {

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
    public ClienteDao toEntity(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        var endereco = mapper.toEnderecoEntity(cliente.getEndereco());

        return new ClienteDao(
                cliente.getUsuarioId(), cliente.getNome(), cliente.getEmail(), cliente.getLogin(), cliente.getSenha(),
                endereco, cliente.getNumeroCartaoFidelidade(), null, null
        );
    }

    @Override
    public Cliente toDomain(ClienteDao entity) {
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
    public ClienteDtoResponse toResponse(ClienteDao entity) {
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

    @Override
    public Page<ClienteDtoResponse> toPageResponse(Page<ClienteDao> entityPage)  {

        List<ClienteDtoResponse> dtos = entityPage.getContent()
                .stream()
                .map(this::toResponse)
                .toList();

        return new PageImpl<>(dtos, entityPage.getPageable(), entityPage.getTotalElements());
    }
}

