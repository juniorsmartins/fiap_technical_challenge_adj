package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.presenters.OutputMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.model.Item;
import br.com.fiap.tech.challenge_user.infrastructure.constant.ConstantsController;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.ItemDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ItemEntity;
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

