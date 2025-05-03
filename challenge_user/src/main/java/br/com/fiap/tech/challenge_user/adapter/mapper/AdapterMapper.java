package br.com.fiap.tech.challenge_user.adapter.mapper;

import br.com.fiap.tech.challenge_user.adapter.dto.request.EnderecoDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.request.UsuarioDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.request.UsuarioUpdateDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.response.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.dto.response.UsuarioDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.adapter.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Endereco;
import br.com.fiap.tech.challenge_user.application.core.domain.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdapterMapper {

    @Mapping(target = "usuarioId", ignore = true)
    Usuario toUsuario(UsuarioDtoRequest usuarioDtoRequest);

    @Mapping(target = "enderecoId", ignore = true)
    Endereco toEndereco(EnderecoDtoRequest enderecoDtoRequest);

    Usuario toUsuario(UsuarioUpdateDtoRequest usuarioUpdateDtoRequest);

    @Mapping(target = "dataHoraCriacao", ignore = true)
    @Mapping(target = "dataHoraEdicao", ignore = true)
    UsuarioDtoResponse toUsuarioDtoResponse(Usuario usuario);

    EnderecoDtoResponse toEnderecoDtoResponse(Endereco endereco);

    UsuarioDtoResponse toUsuarioDtoResponse(UsuarioEntity usuarioEntity);

    EnderecoDtoResponse toEnderecoDtoResponse(EnderecoEntity enderecoEntity);
}

