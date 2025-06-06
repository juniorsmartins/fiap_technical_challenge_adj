package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.port.out.UsuarioSearchOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.dto.filter.UsuarioFiltroDto;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.infrastructure.repository.ProprietarioRepository;
import br.com.fiap.tech.challenge_user.infrastructure.spec.ProprietarioSpecfication;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProprietarioSearchAdapter implements UsuarioSearchOutputPort<ProprietarioEntity> {

    private final ProprietarioRepository repository;

    @Override
    public Page<ProprietarioEntity> search(final UsuarioFiltroDto filtroDto, final Pageable paginacao) {

        return Optional.of(filtroDto)
                .map(dto -> repository.findAll(ProprietarioSpecfication.consultaDinamica(dto), paginacao))
                .orElseThrow();
    }
}

