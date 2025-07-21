package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.interfaces.in.DeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.domain.entities.Restaurante;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.RestauranteDao;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RestauranteDeleteUseCase extends AbstractDeleteUseCase<RestauranteDao>
        implements DeleteByIdInputPort<Restaurante> {

    public RestauranteDeleteUseCase(
            FindByIdOutputPort<RestauranteDao> findByIdOutputPort,
            DeleteOutputPort<RestauranteDao> deleteOutputPort) {
        super(findByIdOutputPort, deleteOutputPort);
    }

    @Override
    public void deleteById(@NonNull final UUID id) {

        super.deleteById(id);
    }
}

