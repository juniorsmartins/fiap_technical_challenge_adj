package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.port.in.DeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.port.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.exception.http404.ProprietarioNotFoundException;
import br.com.fiap.tech.challenge_user.domain.exception.http409.ActiveOwnerBlocksDeletionException;
import br.com.fiap.tech.challenge_user.domain.model.Proprietario;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProprietarioDeleteUseCase implements DeleteByIdInputPort<Proprietario> {

    private final FindByIdOutputPort<ProprietarioEntity> findByIdOutputPort;

    private final DeleteOutputPort<ProprietarioEntity> deleteOutputPort;

    public void deleteById(@NonNull final UUID id) {

        findByIdOutputPort.findById(id)
                .ifPresentOrElse(entity -> {
                    this.checkActiveOwner(entity);
                    deleteOutputPort.delete(entity);
                }, () -> {
                    log.error("ProprietarioDeleteUseCase - Proprietario não encontrado por id: {}.", id);
                    throw new ProprietarioNotFoundException(id);
                });
    }

    private void checkActiveOwner(ProprietarioEntity entity) {

        if (!entity.getRestaurantes().isEmpty()) {
            throw new ActiveOwnerBlocksDeletionException(entity.getUsuarioId().toString());
        }
    }
}

