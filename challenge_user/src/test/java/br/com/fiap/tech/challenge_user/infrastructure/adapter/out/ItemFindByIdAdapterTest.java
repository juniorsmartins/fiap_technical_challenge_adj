package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.gateways.ItemFindByIdAdapter;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ItemEntity;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemFindByIdAdapterTest {

    private final ItemRepository repository;

    private FindByIdOutputPort<ItemEntity> itemFindByIdAdapter;

    @Autowired
    ItemFindByIdAdapterTest(ItemRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        itemFindByIdAdapter = new ItemFindByIdAdapter(repository);
    }

    @Test
    void deveEncontrarItemPorIdComSucesso() {
        // Arrange
        var itemEntity = new ItemEntity();
        itemEntity.setNome("Coca-Cola");
        itemEntity.setDescricao("Refrigerante");
        itemEntity.setPreco(new BigDecimal("20.00"));
        itemEntity.setEntrega(true);
        itemEntity.setFoto("http://link-foto.com.br");
        var savedEntity = repository.save(itemEntity);
        var itemId = savedEntity.getItemId();

        // Act
        Optional<ItemEntity> result = itemFindByIdAdapter.findById(itemId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(itemId, result.get().getItemId());
        assertEquals("Coca-Cola", result.get().getNome());
        assertEquals("Refrigerante", result.get().getDescricao());
        assertEquals(new BigDecimal("20.00"), result.get().getPreco());
        assertTrue(result.get().isEntrega());
        assertEquals("http://link-foto.com.br", result.get().getFoto());
    }

    @Test
    void deveRetornarOptionalVazioQuandoItemNaoEncontrado() {
        // Arrange
        var itemId = UUID.randomUUID();

        // Act
        Optional<ItemEntity> result = itemFindByIdAdapter.findById(itemId);

        // Assert
        assertFalse(result.isPresent());
    }
}

