package br.com.fiap.tech.challenge_user.application.core.mapper;

import br.com.fiap.tech.challenge_user.adapter.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    UsuarioEntity toUsuarioEntity(Usuario usuario);

    Usuario toUsuario(UsuarioEntity usuarioEntity);
}

