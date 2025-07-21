package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.InputPresenter;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.in.CreateInputPort;
import br.com.fiap.tech.challenge_user.domain.entities.Item;
import br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsController;
import br.com.fiap.tech.challenge_user.application.dtos.in.ItemDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.ItemDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ItemDao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_ITENS})
public class ItemCreateController
        extends AbstractCreateController<ItemDtoRequest, ItemDtoResponse, Item, ItemDao> {

    public ItemCreateController(
            InputPresenter<ItemDtoRequest, Item> inputPresenter,
            OutputPresenter<Item, ItemDtoResponse, ItemDao> outputPresenter,
            CreateInputPort<Item> createInputPort) {
        super(inputPresenter, outputPresenter, createInputPort);
    }
}

