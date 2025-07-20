package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.gateways.ItemFindByIdGateway;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ItemDao;
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
class ItemFindByIdGatewayTest {

    private final ItemRepository repository;

    private FindByIdOutputPort<ItemDao> itemFindByIdAdapter;

    @Autowired
    ItemFindByIdGatewayTest(ItemRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        itemFindByIdAdapter = new ItemFindByIdGateway(repository);
    }

    @Test
    void deveEncontrarItemPorIdComSucesso() {
        // Arrange
        var itemEntity = new ItemDao();
        itemEntity.setNome("Coca-Cola");
        itemEntity.setDescricao("Refrigerante");
        itemEntity.setPreco(new BigDecimal("20.00"));
        itemEntity.setEntrega(true);
        itemEntity.setFoto("http://link-foto.com.br");
        var savedEntity = repository.save(itemEntity);
        var itemId = savedEntity.getItemId();

        // Act
        Optional<ItemDao> result = itemFindByIdAdapter.findById(itemId);

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
        Optional<ItemDao> result = itemFindByIdAdapter.findById(itemId);

        // Assert
        assertFalse(result.isPresent());
    }
}

