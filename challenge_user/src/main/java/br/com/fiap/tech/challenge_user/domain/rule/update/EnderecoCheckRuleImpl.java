package br.com.fiap.tech.challenge_user.domain.rule.update;

import br.com.fiap.tech.challenge_user.domain.model.Usuario;
import br.com.fiap.tech.challenge_user.infrastructure.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.infrastructure.entity.UsuarioEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public final class EnderecoCheckRuleImpl<T extends Usuario, E extends UsuarioEntity> implements EnderecoCheckRule<T, E> {

    @Override
    public E updateAddress(T domain, E entity) {

        if (domain.getEndereco() == null && entity.getEndereco() != null) {
            // Cenário: Requisição sem endereço, usuário com endereço → Remover endereço
            entity.setEndereco(null); // orphanRemoval = true remove o endereço do banco

        } else if (domain.getEndereco() != null && entity.getEndereco() == null) {
            // Cenário: Requisição com endereço e usuário sem endereço → Criar novo endereço
            var enderecoEntity = new EnderecoEntity();
            enderecoEntity.setCep(domain.getEndereco().getCep());
            enderecoEntity.setLogradouro(domain.getEndereco().getLogradouro());
            enderecoEntity.setNumero(domain.getEndereco().getNumero());
            entity.setEndereco(enderecoEntity);

        } else if (domain.getEndereco() != null && entity.getEndereco() != null) {
            // Cenário: Requisição com endereço e usuário com endereço → substituir endereço
            BeanUtils.copyProperties(domain.getEndereco(), entity.getEndereco(), "enderecoId");
        }

        // Cenário: Requisição sem endereço e usuário sem endereço -> não fazer nada
        return entity;
    }
}

