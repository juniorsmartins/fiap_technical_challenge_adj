package br.com.fiap.tech.challenge_user.application.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public final class Cliente extends Usuario {

    private String numeroCartaoFidelidade;
}

