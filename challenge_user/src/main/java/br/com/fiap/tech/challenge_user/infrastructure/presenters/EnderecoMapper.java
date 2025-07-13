package br.com.fiap.tech.challenge_user.infrastructure.presenters;

import br.com.fiap.tech.challenge_user.infrastructure.dto.in.EnderecoDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.domain.model.Endereco;
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

