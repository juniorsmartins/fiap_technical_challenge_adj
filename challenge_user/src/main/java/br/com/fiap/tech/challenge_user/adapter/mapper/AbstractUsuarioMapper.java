package br.com.fiap.tech.challenge_user.adapter.mapper;

public interface AbstractUsuarioMapper<I, O, U, T, E> {

    T toDomainIn(I usuario);

    T toDomainOut(E usuario);

    E toEntity(T usuario);

    O toDtoResponse(T usuario);

    O toUsuarioDtoResponse(E usuario);

    T toDomainUpdate(U usuario);
}

