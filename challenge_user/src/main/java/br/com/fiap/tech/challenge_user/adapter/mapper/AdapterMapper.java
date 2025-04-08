package br.com.fiap.tech.challenge_user.adapter.mapper;

import br.com.fiap.tech.challenge_user.adapter.dto.request.UsuarioDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.response.UsuarioDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdapterMapper {

    @Mapping(target = "usuarioId", ignore = true)
    Usuario toUsuario(UsuarioDtoRequest usuarioDtoRequest);

    UsuarioDtoResponse toUsuarioDtoResponse(Usuario usuario);

    UsuarioDtoResponse toUsuarioDtoResponse(UsuarioEntity usuarioEntity);
}

