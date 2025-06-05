package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.port.out.UsuarioSearchOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.dto.filter.UsuarioFiltroDto;
import br.com.fiap.tech.challenge_user.infrastructure.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.infrastructure.repository.UsuarioRepository;
import br.com.fiap.tech.challenge_user.infrastructure.spec.UsuarioSpecfication;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UsuarioSearchAdapter<E extends UsuarioEntity> implements UsuarioSearchOutputPort<E> {

    private final UsuarioRepository repository;

    @Override
    public Page<E> search(final UsuarioFiltroDto filtroDto, final Pageable paginacao) {

        return Optional.of(filtroDto)
                .map(dto -> (Page<E>) repository.findAll(UsuarioSpecfication.consultaDinamica(dto), paginacao))
                .orElseThrow();
    }
}

