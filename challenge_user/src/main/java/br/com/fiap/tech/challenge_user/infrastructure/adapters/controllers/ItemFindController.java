package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.models.Item;
import br.com.fiap.tech.challenge_user.infrastructure.constants.ConstantsController;
import br.com.fiap.tech.challenge_user.application.dtos.out.ItemDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ItemEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_ITENS})
public class ItemFindController extends AbstractFindController<ItemDtoResponse, Item, ItemEntity> {

    public ItemFindController(
            OutputMapper<Item, ItemDtoResponse, ItemEntity> outputMapper,
            FindByIdOutputPort<ItemEntity> findByIdOutputPort) {
        super(outputMapper, findByIdOutputPort);
    }
}

