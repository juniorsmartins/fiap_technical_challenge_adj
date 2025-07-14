package br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.InputMapper;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.in.UpdateInputPort;
import br.com.fiap.tech.challenge_user.domain.model.Item;
import br.com.fiap.tech.challenge_user.infrastructure.constant.ConstantsController;
import br.com.fiap.tech.challenge_user.application.dtos.in.ItemDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.ItemDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ItemEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_ITENS})
public class ItemUpdateController
        extends AbstractUpdateController<ItemDtoRequest, ItemDtoResponse, Item, ItemEntity> {

    public ItemUpdateController(
            InputMapper<ItemDtoRequest, Item> inputMapper,
            OutputMapper<Item, ItemDtoResponse, ItemEntity> outputMapper,
            UpdateInputPort<Item> updateInputPort) {
        super(inputMapper, outputMapper, updateInputPort);
    }
}

