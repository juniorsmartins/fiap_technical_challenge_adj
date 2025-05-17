package br.com.fiap.tech.challenge_user.adapter.repository;

import br.com.fiap.tech.challenge_user.adapter.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.application.port.output.ClienteFindByIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ClienteFindByIdAdapter implements ClienteFindByIdOutputPort {

    private final ClienteRepository clienteRepository;

    @Override
    public Optional<ClienteEntity> findById(@NonNull final UUID id) {
        return clienteRepository.findById(id);
    }
}

