package br.com.fiap.tech.challenge_user.adapter.mapper;

import br.com.fiap.tech.challenge_user.adapter.dto.request.EnderecoDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.response.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Endereco;
import org.springframework.stereotype.Component;

@Component
public final class EnderecoMapper {

    public Endereco toEnderecoIn(EnderecoDtoRequest dto) {
        if (dto == null) {
            return null;
        }

        return Endereco.builder()
                .cep(dto.cep())
                .logradouro(dto.logradouro())
                .numero(dto.numero())
                .build();
    }

    EnderecoDtoResponse toEnderecoDtoResponse(Endereco endereco) {
        if (endereco == null) {
            return null;
        }

        return new EnderecoDtoResponse(
                endereco.getEnderecoId(), endereco.getCep(), endereco.getLogradouro(), endereco.getNumero()
        );
    }

    EnderecoDtoResponse toEnderecoDtoResponse(EnderecoEntity entity) {
        if (entity == null) {
            return null;
        }

        return new EnderecoDtoResponse(
                entity.getEnderecoId(), entity.getCep(), entity.getLogradouro(), entity.getNumero()
        );
    }

    EnderecoEntity toEnderecoEntity(Endereco endereco) {
        if (endereco == null) {
            return null;
        }

        return EnderecoEntity.builder()
                .enderecoId(endereco.getEnderecoId())
                .cep(endereco.getCep())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .build();
    }

    Endereco toEnderecoOut(EnderecoEntity entity) {
        if (entity == null) {
            return null;
        }

        return Endereco.builder()
                .enderecoId(entity.getEnderecoId())
                .cep(entity.getCep())
                .logradouro(entity.getLogradouro())
                .numero(entity.getNumero())
                .build();
    }
}

