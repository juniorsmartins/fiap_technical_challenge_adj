package br.com.fiap.tech.challenge_user.application.port.out;

import br.com.fiap.tech.challenge_user.infrastructure.dto.filter.UsuarioFiltroDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioSearchOutputPort<E> {

    Page<E> search(UsuarioFiltroDto filtroDto, Pageable pageable);
}

