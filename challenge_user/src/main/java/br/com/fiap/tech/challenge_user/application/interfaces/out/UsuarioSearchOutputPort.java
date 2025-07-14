package br.com.fiap.tech.challenge_user.application.interfaces.out;

import br.com.fiap.tech.challenge_user.application.dtos.filters.UsuarioFiltroDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioSearchOutputPort<E> {

    Page<E> search(UsuarioFiltroDto filtroDto, Pageable pageable);
}

