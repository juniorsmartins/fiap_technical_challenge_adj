package br.com.fiap.tech.challenge_user.application.port.output;

import br.com.fiap.tech.challenge_user.adapter.entity.ProprietarioEntity;

public interface ProprietarioCreateOutputPort {

    ProprietarioEntity save(ProprietarioEntity proprietarioEntity);
}

