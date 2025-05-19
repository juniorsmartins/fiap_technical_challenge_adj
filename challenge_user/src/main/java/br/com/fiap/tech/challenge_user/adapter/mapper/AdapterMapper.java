package br.com.fiap.tech.challenge_user.adapter.mapper;

import br.com.fiap.tech.challenge_user.adapter.dto.request.*;
import br.com.fiap.tech.challenge_user.adapter.dto.response.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.dto.response.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.dto.response.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.adapter.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.adapter.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Cliente;
import br.com.fiap.tech.challenge_user.application.core.domain.Endereco;
import br.com.fiap.tech.challenge_user.application.core.domain.Proprietario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdapterMapper {

    @Mapping(target = "usuarioId", ignore = true)
    Cliente toCliente(ClienteDtoRequest clienteDtoRequest);

    @Mapping(target = "enderecoId", ignore = true)
    Endereco toEndereco(EnderecoDtoRequest enderecoDtoRequest);

    Cliente toCliente(ClienteUpdateDtoRequest clienteUpdateDtoRequest);

    @Mapping(target = "dataHoraCriacao", ignore = true)
    @Mapping(target = "dataHoraEdicao", ignore = true)
    ClienteDtoResponse toClienteDtoResponse(Cliente cliente);

    EnderecoDtoResponse toEnderecoDtoResponse(Endereco endereco);

    ClienteDtoResponse toClienteDtoResponse(ClienteEntity clienteEntity);

    EnderecoDtoResponse toEnderecoDtoResponse(EnderecoEntity enderecoEntity);

    @Mapping(target = "usuarioId", ignore = true)
    Proprietario toProprietario(ProprietarioDtoRequest proprietarioDtoRequest);

    @Mapping(target = "dataHoraCriacao", ignore = true)
    @Mapping(target = "dataHoraEdicao", ignore = true)
    ProprietarioDtoResponse toProprietarioDtoResponse(Proprietario proprietario);

    ProprietarioDtoResponse toProprietarioDtoResponse(ProprietarioEntity proprietarioEntity);

    Proprietario toProprietario(ProprietarioUpdateDtoRequest proprietarioUpdateDtoRequest);
}

