package br.com.fiap.tech.challenge_user.application.mapper;

import org.springframework.data.domain.Page;

public interface OutputMapper<T, O, E> {

    O toDtoResponse(T domain);

    O toResponse(E entity);

    Page<O> toPageResponse(Page<E> entityPage);
}

