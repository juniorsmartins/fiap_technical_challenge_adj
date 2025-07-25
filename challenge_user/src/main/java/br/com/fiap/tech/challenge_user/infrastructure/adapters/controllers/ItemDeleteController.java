package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.application.interfaces.in.DeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.domain.entities.Item;
import br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_ITENS})
public class ItemDeleteController extends AbstractDeleteController<Item> {

    public ItemDeleteController(DeleteByIdInputPort<Item> deleteByIdInputPort) {
        super(deleteByIdInputPort);
    }
}

