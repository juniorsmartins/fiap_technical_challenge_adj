package br.com.fiap.tech.challenge_user.application.core.utils;

import br.com.fiap.tech.challenge_user.adapter.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.adapter.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Usuario;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public final class UpdateUserAndAddress<T extends Usuario, E extends UsuarioEntity> {

    public E upUsuario(T usuario, E entity) {
        BeanUtils.copyProperties(usuario, entity, "usuarioId", "dataHoraCriacao", "dataHoraEdicao", "endereco");
        return upEndereco(usuario, entity);
    }

    private E upEndereco(T usuario, E entity) {

        if (usuario.getEndereco() == null && entity.getEndereco() != null) {
            // Cenário: Requisição sem endereço, usuário com endereço → Remover endereço
            entity.setEndereco(null); // orphanRemoval = true remove o endereço do banco

        } else if (usuario.getEndereco() != null && entity.getEndereco() == null) {
            // Cenário: Requisição com endereço e usuário sem endereço → Criar novo endereço
            entity.setEndereco(EnderecoEntity.builder()
                    .cep(usuario.getEndereco().getCep())
                    .logradouro(usuario.getEndereco().getLogradouro())
                    .numero(usuario.getEndereco().getNumero())
                    .build()
            );

        } else if (usuario.getEndereco() != null && entity.getEndereco() != null) {
            // Cenário: Requisição com endereço e usuário com endereço → substituir endereço
            BeanUtils.copyProperties(usuario.getEndereco(), entity.getEndereco(), "enderecoId");
        }

        return entity;
    }
}

