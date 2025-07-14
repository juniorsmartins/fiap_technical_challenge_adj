package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.presenters.InputMapper;
import br.com.fiap.tech.challenge_user.infrastructure.presenters.OutputMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.in.CreateInputPort;
import br.com.fiap.tech.challenge_user.domain.model.Item;
import br.com.fiap.tech.challenge_user.infrastructure.constant.ConstantsController;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.ItemDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.ItemDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ItemEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {ConstantsController.URI_ITENS})
public class ItemCreateController
        extends AbstractCreateController<ItemDtoRequest, ItemDtoResponse, Item, ItemEntity> {

    public ItemCreateController(
            InputMapper<ItemDtoRequest, Item> inputMapper,
            OutputMapper<Item, ItemDtoResponse, ItemEntity> outputMapper,
            CreateInputPort<Item> createInputPort) {
        super(inputMapper, outputMapper, createInputPort);
    }
}

