package br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters;

import org.springframework.data.domain.Page;

public interface PageMapper<O, E> {

    Page<O> toPageResponse(Page<E> entityPage);
}

