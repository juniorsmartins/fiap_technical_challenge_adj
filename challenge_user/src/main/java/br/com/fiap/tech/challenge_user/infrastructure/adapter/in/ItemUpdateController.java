package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.application.mapper.InputMapper;
import br.com.fiap.tech.challenge_user.application.mapper.OutputMapper;
import br.com.fiap.tech.challenge_user.application.port.in.UpdateInputPort;
import br.com.fiap.tech.challenge_user.domain.model.Item;
import br.com.fiap.tech.challenge_user.infrastructure.constant.ConstantsController;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.ItemDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.ItemDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ItemEntity;
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

