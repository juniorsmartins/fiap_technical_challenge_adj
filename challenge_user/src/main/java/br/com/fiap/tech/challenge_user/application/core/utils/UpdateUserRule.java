package br.com.fiap.tech.challenge_user.application.core.utils;

import br.com.fiap.tech.challenge_user.adapter.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.adapter.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Usuario;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public final class UpdateUserRule<T extends Usuario, E extends UsuarioEntity> {

    public E upUsuario(T usuario, E entity) {
        BeanUtils.copyProperties(usuario, entity, "usuarioId", "dataHoraCriacao", "dataHoraEdicao", "endereco");
        return upEndereco(usuario, entity);
    }

    private E upEndereco(T usuario, E entity) {

        if (usuario.getEndereco() == null && entity.getEndereco() == null) {
            // Cenário 1: Requisição sem endereço, usuário sem endereço → Não fazer nada
            return entity;

        } else if (usuario.getEndereco() == null && entity.getEndereco() != null) {
            // Cenário 2: Requisição sem endereço, usuário com endereço → Remover endereço
            entity.setEndereco(null); // orphanRemoval = true remove o endereço do banco

        } else if (usuario.getEndereco() != null && entity.getEndereco() == null) {
            // Cenário 3: Requisição com endereço, usuário sem endereço → Criar novo endereço
            entity.setEndereco(EnderecoEntity.builder()
                    .cep(usuario.getEndereco().getCep())
                    .logradouro(usuario.getEndereco().getLogradouro())
                    .numero(usuario.getEndereco().getNumero())
                    .build()
            );

        } else if (usuario.getEndereco() != null && entity.getEndereco() != null) {
            // Cenário 4: Requisição com endereço, usuário com endereço → Atualizar endereço
            BeanUtils.copyProperties(usuario.getEndereco(), entity.getEndereco(), "enderecoId");
        }

        return entity;
    }
}

