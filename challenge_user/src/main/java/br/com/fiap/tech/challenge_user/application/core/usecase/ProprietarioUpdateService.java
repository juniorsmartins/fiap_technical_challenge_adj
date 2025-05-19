package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.adapter.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.adapter.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Proprietario;
import br.com.fiap.tech.challenge_user.application.core.mapper.ApplicationMapper;
import br.com.fiap.tech.challenge_user.application.port.input.ProprietarioUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.ProprietarioCreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.ProprietarioFindByIdOutputPort;
import br.com.fiap.tech.challenge_user.config.exceptions.http404.ClienteNotFoundException;
import br.com.fiap.tech.challenge_user.config.exceptions.http404.ProprietarioNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProprietarioUpdateService implements ProprietarioUpdateInputPort {

    private final ProprietarioFindByIdOutputPort proprietarioFindByIdOutputPort;

    private final ProprietarioCreateOutputPort proprietarioCreateOutputPort;

    private final ApplicationMapper applicationMapper;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Modifying
    @Override
    public Proprietario update(@NonNull final Proprietario proprietario) {

        log.info("ProprietarioUpdateService - entrada no service de update: {}", proprietario);

        var id = proprietario.getUsuarioId();

        var proprietarioUpdated = proprietarioFindByIdOutputPort.findById(id)
                .map(entity -> atualizarUsuario(proprietario, entity))
                .map(entity -> atualizarEndereco(proprietario, entity))
                .map(proprietarioCreateOutputPort::save)
                .map(applicationMapper::toProprietario)
                .orElseThrow(() -> {
                    log.error("ProprietarioUpdateService - Proprietário não encontrado por id: {}.", id);
                    return new ProprietarioNotFoundException(id);
                });

        log.info("ProprietarioUpdateService - concluído serviço de update: {}", proprietarioUpdated);

        return proprietarioUpdated;
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

