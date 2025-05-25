package br.com.fiap.tech.challenge_user.adapter.mapper;

import br.com.fiap.tech.challenge_user.adapter.dto.request.EnderecoDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.response.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    @Mapping(target = "enderecoId", ignore = true)
    Endereco toEndereco(EnderecoDtoRequest enderecoDtoRequest);

    EnderecoDtoResponse toEnderecoDtoResponse(Endereco endereco);

    EnderecoDtoResponse toEnderecoDtoResponse(EnderecoEntity enderecoEntity);

    EnderecoEntity toEnderecoEntity(Endereco endereco);

    Endereco toEndereco(EnderecoEntity enderecoEntity);
}

