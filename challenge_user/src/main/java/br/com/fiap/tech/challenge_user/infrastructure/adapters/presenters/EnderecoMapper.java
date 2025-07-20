package br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters;

import br.com.fiap.tech.challenge_user.application.dtos.in.EnderecoDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.EnderecoDao;
import br.com.fiap.tech.challenge_user.domain.models.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    @Mapping(target = "enderecoId", ignore = true)
    Endereco toEndereco(EnderecoDtoRequest enderecoDtoRequest);

    EnderecoDtoResponse toEnderecoDtoResponse(Endereco endereco);

    EnderecoDtoResponse toEnderecoDtoResponse(EnderecoDao enderecoDao);

    EnderecoDao toEnderecoEntity(Endereco endereco);

    Endereco toEndereco(EnderecoDao enderecoDao);
}

