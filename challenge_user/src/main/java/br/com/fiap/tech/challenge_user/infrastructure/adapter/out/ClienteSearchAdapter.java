package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.port.out.UsuarioSearchOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.dto.filter.UsuarioFiltroDto;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.infrastructure.repository.ClienteRepository;
import br.com.fiap.tech.challenge_user.infrastructure.spec.ClienteSpecfication;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClienteSearchAdapter implements UsuarioSearchOutputPort<ClienteEntity> {

    private final ClienteRepository repository;

    @Override
    public Page<ClienteEntity> search(final UsuarioFiltroDto filtroDto, final Pageable paginacao) {

        return Optional.of(filtroDto)
                .map(dto -> repository.findAll(ClienteSpecfication.consultaDinamica(dto), paginacao))
                .orElseThrow();
    }
}

