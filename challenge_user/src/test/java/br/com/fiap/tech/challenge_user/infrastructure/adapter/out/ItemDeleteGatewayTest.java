package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.interfaces.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.gateways.ItemDeleteGateway;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ItemDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemDeleteGatewayTest {

    private final ItemRepository repository;

    private DeleteOutputPort<ItemDao> itemDeleteAdapter;

    @Autowired
    ItemDeleteGatewayTest(ItemRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        itemDeleteAdapter = new ItemDeleteGateway(repository);
    }

    @Test
    void deveDeletarItemComSucesso() {
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
        itemDeleteAdapter.delete(savedEntity);

        // Assert
        assertFalse(repository.existsById(itemId), "O item deve ser removido do banco");
    }

    @Test
    void deveNaoLancarExcecaoQuandoDeletarItemNaoPersistido() {
        // Arrange
        var itemEntity = new ItemDao();
        itemEntity.setItemId(UUID.randomUUID());
        itemEntity.setNome("Coca-Cola");
        itemEntity.setDescricao("Refrigerante");
        itemEntity.setPreco(new BigDecimal("20.00"));
        itemEntity.setEntrega(true);
        itemEntity.setFoto("http://link-foto.com.br");

        // Act & Assert
        assertDoesNotThrow(() -> itemDeleteAdapter.delete(itemEntity),
                "Deletar uma entidade não persistida não deve lançar exceção");
        assertFalse(repository.existsById(itemEntity.getItemId()),
                "A entidade não deve estar no banco");
    }
}

