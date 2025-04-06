package br.com.fiap.tech.challenge_user.adapter.mapper;

import br.com.fiap.tech.challenge_user.adapter.dto.UsuarioDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.UsuarioDtoResponse;
import br.com.fiap.tech.challenge_user.application.core.domain.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CentralMapper {

    @Mapping(target = "usuarioId", ignore = true)
    Usuario toUsuario(UsuarioDtoRequest usuarioDtoRequest);

    UsuarioDtoResponse toUsuarioDtoResponse(Usuario usuario);
}

