package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.adapter.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.adapter.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.adapter.mapper.AbstractUsuarioMapper;
import br.com.fiap.tech.challenge_user.application.core.domain.Proprietario;
import br.com.fiap.tech.challenge_user.application.port.input.UsuarioUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioCreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.UsuarioFindByIdOutputPort;
import br.com.fiap.tech.challenge_user.config.exceptions.http404.UsuarioNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Proprietario2UpdateService implements UsuarioUpdateInputPort<Proprietario> {

    private final AbstractUsuarioMapper<?, ?, ?, Proprietario, ProprietarioEntity> mapper;

    private final UsuarioFindByIdOutputPort<ProprietarioEntity> findByIdOutputPort;

    private final UsuarioCreateOutputPort<ProprietarioEntity> createOutputPort;

    @Override
    public Proprietario update(@NonNull Proprietario usuario) {

        var id = usuario.getUsuarioId();

        return findByIdOutputPort.findById(id)
                .map(entity -> atualizarUsuario(usuario, entity))
                .map(entity -> atualizarEndereco(usuario, entity))
                .map(createOutputPort::save)
                .map(mapper::toUsuarioOut)
                .orElseThrow(() -> {
                    log.error("Proprietario2UpdateService - Usuário não encontrado por id: {}.", id);
                    return new UsuarioNotFoundException(id);
                });
    }

    private ProprietarioEntity atualizarUsuario(Proprietario proprietario, ProprietarioEntity proprietarioEntity) {
        BeanUtils.copyProperties(proprietario, proprietarioEntity, "usuarioId", "dataHoraCriacao", "dataHoraEdicao", "endereco");
        return proprietarioEntity;
    }

    private ProprietarioEntity atualizarEndereco(Proprietario proprietario, ProprietarioEntity proprietarioEntity) {

        if (proprietario.getEndereco() == null && proprietarioEntity.getEndereco() == null) {
            // Cenário 1: Requisição sem endereço, usuário sem endereço → Não fazer nada
            return proprietarioEntity;

        } else if (proprietario.getEndereco() == null && proprietarioEntity.getEndereco() != null) {
            // Cenário 2: Requisição sem endereço, usuário com endereço → Remover endereço
            proprietarioEntity.setEndereco(null); // orphanRemoval = true remove o endereço do banco

        } else if (proprietario.getEndereco() != null && proprietarioEntity.getEndereco() == null) {
            // Cenário 3: Requisição com endereço, usuário sem endereço → Criar novo endereço
            proprietarioEntity.setEndereco(EnderecoEntity.builder()
                    .cep(proprietario.getEndereco().getCep())
                    .logradouro(proprietario.getEndereco().getLogradouro())
                    .numero(proprietario.getEndereco().getNumero())
                    .build()
            );

        } else if (proprietario.getEndereco() != null && proprietarioEntity.getEndereco() != null) {
            // Cenário 4: Requisição com endereço, usuário com endereço → Atualizar endereço
            BeanUtils.copyProperties(proprietario.getEndereco(), proprietarioEntity.getEndereco(), "enderecoId");
        }

        return proprietarioEntity;
    }
}

