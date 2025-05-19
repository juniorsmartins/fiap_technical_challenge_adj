package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.adapter.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.adapter.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.application.core.domain.Cliente;
import br.com.fiap.tech.challenge_user.application.core.mapper.ApplicationMapper;
import br.com.fiap.tech.challenge_user.application.port.input.ClienteUpdateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.ClienteCreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.output.ClienteFindByIdOutputPort;
import br.com.fiap.tech.challenge_user.config.exceptions.http404.ClienteNotFoundException;
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
public class ClienteUpdateService implements ClienteUpdateInputPort {

    private final ClienteFindByIdOutputPort clienteFindByIdOutputPort;

    private final ClienteCreateOutputPort clienteCreateOutputPort;

    private final ApplicationMapper applicationMapper;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Modifying
    @Override
    public Cliente update(@NonNull final Cliente cliente) {

        log.info("ClienteUpdateService - entrada no service de update: {}", cliente);

        var id = cliente.getUsuarioId();

        var clienteUpdated = clienteFindByIdOutputPort.findById(id)
                .map(entity -> atualizarUsuario(cliente, entity))
                .map(entity -> atualizarEndereco(cliente, entity))
                .map(clienteCreateOutputPort::save)
                .map(applicationMapper::toCliente)
                .orElseThrow(() -> {
                    log.error("ClienteUpdateService - Cliente não encontrado por id: {}.", id);
                    return new ClienteNotFoundException(id);
                });

        log.info("ClienteUpdateService - concluído serviço de update: {}", clienteUpdated);

        return clienteUpdated;
    }

    private ClienteEntity atualizarUsuario(Cliente cliente, ClienteEntity clienteEntity) {
        BeanUtils.copyProperties(cliente, clienteEntity, "usuarioId", "dataHoraCriacao", "dataHoraEdicao", "endereco");
        return clienteEntity;
    }

    private ClienteEntity atualizarEndereco(Cliente cliente, ClienteEntity clienteEntity) {

        if (cliente.getEndereco() == null && clienteEntity.getEndereco() == null) {
            // Cenário 1: Requisição sem endereço, usuário sem endereço → Não fazer nada
            return clienteEntity;

        } else if (cliente.getEndereco() == null && clienteEntity.getEndereco() != null) {
            // Cenário 2: Requisição sem endereço, usuário com endereço → Remover endereço
            clienteEntity.setEndereco(null); // orphanRemoval = true remove o endereço do banco

        } else if (cliente.getEndereco() != null && clienteEntity.getEndereco() == null) {
            // Cenário 3: Requisição com endereço, usuário sem endereço → Criar novo endereço
            clienteEntity.setEndereco(EnderecoEntity.builder()
                    .cep(cliente.getEndereco().getCep())
                    .logradouro(cliente.getEndereco().getLogradouro())
                    .numero(cliente.getEndereco().getNumero())
                    .build()
            );

        } else if (cliente.getEndereco() != null && clienteEntity.getEndereco() != null) {
            // Cenário 4: Requisição com endereço, usuário com endereço → Atualizar endereço
            BeanUtils.copyProperties(cliente.getEndereco(), clienteEntity.getEndereco(), "enderecoId");
        }

        return clienteEntity;
    }
}

