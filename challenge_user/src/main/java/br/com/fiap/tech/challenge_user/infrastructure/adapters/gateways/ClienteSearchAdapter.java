package br.com.fiap.tech.challenge_user.infrastructure.adapters.gateways;

import br.com.fiap.tech.challenge_user.application.interfaces.out.UsuarioSearchOutputPort;
import br.com.fiap.tech.challenge_user.application.dtos.filters.UsuarioFiltroDto;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ClienteEntity;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.ClienteRepository;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.specs.ClienteSpecfication;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClienteSearchAdapter implements UsuarioSearchOutputPort<ClienteEntity> {

    private final ClienteRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Page<ClienteEntity> search(final UsuarioFiltroDto filtroDto, final Pageable paginacao) {

        return Optional.of(filtroDto)
                .map(dto -> repository.findAll(ClienteSpecfication.consultaDinamica(dto), paginacao))
                .orElseThrow();
    }
}

