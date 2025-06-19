package br.com.fiap.tech.challenge_user.domain.exception.http409;

public final class ActiveOwnerBlocksDeletionException extends RegraDeNegocioVioladaException {

    public ActiveOwnerBlocksDeletionException(String proprietarioId) {
        super("exception.conflict.active-owner-blocks-deletion", proprietarioId);
    }
}

