package br.com.fiap.tech.challenge_user.application.core.mapper;

import br.com.fiap.tech.challenge_user.adapter.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.adapter.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Cliente;
import br.com.fiap.tech.challenge_user.application.core.domain.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Mapping(target = "dataHoraCriacao", ignore = true)
    @Mapping(target = "dataHoraEdicao", ignore = true)
    ClienteEntity toClienteEntity(Cliente cliente);

    EnderecoEntity toEnderecoEntity(Endereco endereco);

    Cliente toCliente(ClienteEntity clienteEntity);

    Endereco toEndereco(EnderecoEntity enderecoEntity);
}

