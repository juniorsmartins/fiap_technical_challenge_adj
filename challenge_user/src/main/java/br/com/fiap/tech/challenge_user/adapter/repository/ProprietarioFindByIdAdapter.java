package br.com.fiap.tech.challenge_user.adapter.repository;

import br.com.fiap.tech.challenge_user.adapter.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.application.port.output.ProprietarioFindByIdOutputPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProprietarioFindByIdAdapter implements ProprietarioFindByIdOutputPort {

    private final ProprietarioRepository proprietarioRepository;

    @Override
    public Optional<ProprietarioEntity> findById(@NonNull final UUID id) {
        return proprietarioRepository.findById(id);
    }
}

