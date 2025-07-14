package br.com.fiap.tech.challenge_user.infrastructure.adapters.gateways;

import br.com.fiap.tech.challenge_user.application.interfaces.out.UsuarioSearchOutputPort;
import br.com.fiap.tech.challenge_user.application.dtos.filters.UsuarioFiltroDto;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.ProprietarioRepository;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.specs.ProprietarioSpecfication;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProprietarioSearchAdapter implements UsuarioSearchOutputPort<ProprietarioEntity> {

    private final ProprietarioRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Page<ProprietarioEntity> search(final UsuarioFiltroDto filtroDto, final Pageable paginacao) {

        return Optional.of(filtroDto)
                .map(dto -> repository.findAll(ProprietarioSpecfication.consultaDinamica(dto), paginacao))
                .orElseThrow();
    }
}

