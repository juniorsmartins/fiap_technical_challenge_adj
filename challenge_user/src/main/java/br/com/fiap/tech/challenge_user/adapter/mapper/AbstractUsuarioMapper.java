package br.com.fiap.tech.challenge_user.adapter.mapper;

public interface AbstractUsuarioMapper<I, O, U, T, E> {

    T toUsuarioIn(I usuario);

    T toUsuarioOut(E usuario);

    E toUsuarioEntity(T usuario);

    O toDtoResponse(T usuario);

    O toUsuarioDtoResponse(E usuario);

    T toUsuarioIn2(U usuario);
}

