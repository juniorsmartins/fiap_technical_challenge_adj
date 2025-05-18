package br.com.fiap.tech.challenge_user.application.core.usecase;

import br.com.fiap.tech.challenge_user.application.core.domain.Proprietario;
import br.com.fiap.tech.challenge_user.application.core.mapper.ApplicationMapper;
import br.com.fiap.tech.challenge_user.application.port.input.ProprietarioCreateInputPort;
import br.com.fiap.tech.challenge_user.application.port.output.ProprietarioCreateOutputPort;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProprietarioCreateService implements ProprietarioCreateInputPort {

    private final ProprietarioCreateOutputPort proprietarioCreateOutputPort;

    private final ApplicationMapper applicationMapper;

    @Override
    public Proprietario create(@NotNull final Proprietario proprietario) {

        log.info("ProprietarioCreateService - entrada no service de create: {}", proprietario);

        var proprietarioCreated = Optional.of(proprietario)
                .map(applicationMapper::toProprietarioEntity)
                .map(proprietarioCreateOutputPort::save)
                .map(applicationMapper::toProprietario)
                .orElseThrow();

        log.info("ProprietarioCreateService - concluído serviço de create: {}", proprietarioCreated);

        return proprietarioCreated;
    }
}

