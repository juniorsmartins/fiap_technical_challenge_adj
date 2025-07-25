package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.interfaces.in.DeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.ProprietarioNotFoundException;
import br.com.fiap.tech.challenge_user.application.exception.http409.ActiveOwnerBlocksDeletionException;
import br.com.fiap.tech.challenge_user.domain.entities.Proprietario;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProprietarioDeleteUseCase implements DeleteByIdInputPort<Proprietario> {

    private final FindByIdOutputPort<ProprietarioDao> findByIdOutputPort;

    private final DeleteOutputPort<ProprietarioDao> deleteOutputPort;

    @Transactional
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

    private void checkActiveOwner(ProprietarioDao entity) {

        if (!entity.getRestaurantes().isEmpty()) {
            throw new ActiveOwnerBlocksDeletionException(entity.getUsuarioId().toString());
        }
    }
}

