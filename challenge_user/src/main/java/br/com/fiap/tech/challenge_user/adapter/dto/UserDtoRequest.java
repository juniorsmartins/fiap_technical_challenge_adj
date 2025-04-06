package br.com.fiap.tech.challenge_user.adapter.dto;

public record UserDtoRequest(

        String nome,

        String email,

        String login,

        String senha
) { }

