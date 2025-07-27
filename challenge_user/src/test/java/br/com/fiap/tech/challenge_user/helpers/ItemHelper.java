package br.com.fiap.tech.challenge_user.helpers;

import br.com.fiap.tech.challenge_user.domain.entities.Item;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class ItemHelper {

    public static Item gerarComId() {

        return new Item(
                UUID.randomUUID(),
                "Pizza Margherita",
                "Pizza com molho de tomate, mussarela e manjericão",
                new BigDecimal("45.90"),
                true,
                "pizza_margherita.jpg");
    }
}

