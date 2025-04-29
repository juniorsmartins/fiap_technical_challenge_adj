package br.com.fiap.tech.challenge_user.application.core.mapper;

import br.com.fiap.tech.challenge_user.adapter.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.adapter.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Endereco;
import br.com.fiap.tech.challenge_user.application.core.domain.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    UsuarioEntity toUsuarioEntity(Usuario usuario);

    EnderecoEntity toEnderecoEntity(Endereco endereco);

    Usuario toUsuario(UsuarioEntity usuarioEntity);

    Endereco toEndereco(EnderecoEntity enderecoEntity);
}

