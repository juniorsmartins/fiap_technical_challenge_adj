package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.entities.Item;
import br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsController;
import br.com.fiap.tech.challenge_user.application.dtos.out.ItemDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ItemDao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_ITENS})
public class ItemFindController extends AbstractFindController<ItemDtoResponse, Item, ItemDao> {

    public ItemFindController(
            OutputPresenter<Item, ItemDtoResponse, ItemDao> outputPresenter,
            FindByIdOutputPort<ItemDao> findByIdOutputPort) {
        super(outputPresenter, findByIdOutputPort);
    }
}

