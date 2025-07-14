package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.interfaces.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ItemEntity;
import br.com.fiap.tech.challenge_user.infrastructure.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemDeleteAdapterTest {

    private final ItemRepository repository;

    private DeleteOutputPort<ItemEntity> itemDeleteAdapter;

    @Autowired
    ItemDeleteAdapterTest(ItemRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        itemDeleteAdapter = new ItemDeleteAdapter(repository);
    }

    @Test
    void deveDeletarItemComSucesso() {
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
        itemDeleteAdapter.delete(savedEntity);

        // Assert
        assertFalse(repository.existsById(itemId), "O item deve ser removido do banco");
    }

    @Test
    void deveNaoLancarExcecaoQuandoDeletarItemNaoPersistido() {
        // Arrange
        var itemEntity = new ItemEntity();
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

