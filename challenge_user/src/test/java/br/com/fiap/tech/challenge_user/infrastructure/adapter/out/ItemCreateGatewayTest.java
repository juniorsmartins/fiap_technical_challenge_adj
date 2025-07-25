package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http500.ItemNonPersistenceException;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.gateways.ItemCreateGateway;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ItemDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemCreateGatewayTest {

    private final ItemRepository repository;

    private CreateOutputPort<ItemDao> itemCreateAdapter;

    @Autowired
    ItemCreateGatewayTest(ItemRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        itemCreateAdapter = new ItemCreateGateway(repository);
    }

    @Test
    void deveSalvarItemComSucesso() {
        // Arrange
        var itemEntity = new ItemDao();
        itemEntity.setNome("Coca-Cola");
        itemEntity.setDescricao("Refrigerante");
        itemEntity.setPreco(new BigDecimal("20.00"));
        itemEntity.setEntrega(true);
        itemEntity.setFoto("http://link-foto.com.br");

        // Act
        var savedEntity = itemCreateAdapter.save(itemEntity);

        // Assert
        assertNotNull(savedEntity);
        assertNotNull(savedEntity.getItemId());
        assertEquals("Coca-Cola", savedEntity.getNome());
        assertEquals("Refrigerante", savedEntity.getDescricao());
        assertEquals(new BigDecimal("20.00"), savedEntity.getPreco());
        assertTrue(savedEntity.isEntrega());
        assertEquals("http://link-foto.com.br", savedEntity.getFoto());

        // Verifica persistência no banco
        var persistedEntity = repository.findById(savedEntity.getItemId());
        assertTrue(persistedEntity.isPresent());
        assertEquals(savedEntity.getNome(), persistedEntity.get().getNome());
        assertEquals(savedEntity.getPreco(), persistedEntity.get().getPreco());
    }

    @Test
    void deveLancarExcecaoQuandoNomeNulo() {
        // Arrange
        var itemEntity = new ItemDao();
        itemEntity.setDescricao("Refrigerante");
        itemEntity.setPreco(new BigDecimal("20.00"));
        itemEntity.setEntrega(true);
        itemEntity.setFoto("http://link-foto.com.br");

        // Act & Assert
        assertThrows(ItemNonPersistenceException.class, () -> itemCreateAdapter.save(itemEntity));
    }

    @Test
    void deveLancarExcecaoQuandoDescricaoNulo() {
        // Arrange
        var itemEntity = new ItemDao();
        itemEntity.setNome("Coca-Cola");
        itemEntity.setPreco(new BigDecimal("20.00"));
        itemEntity.setEntrega(true);
        itemEntity.setFoto("http://link-foto.com.br");

        // Act & Assert
        assertThrows(ItemNonPersistenceException.class, () -> itemCreateAdapter.save(itemEntity));
    }

    @Test
    void deveLancarExcecaoQuandoPrecoNulo() {
        // Arrange
        var itemEntity = new ItemDao();
        itemEntity.setNome("Coca-Cola");
        itemEntity.setDescricao("Refrigerante");
        itemEntity.setEntrega(true);
        itemEntity.setFoto("http://link-foto.com.br");

        // Act & Assert
        assertThrows(ItemNonPersistenceException.class, () -> itemCreateAdapter.save(itemEntity));
    }

    @Test
    void deveLancarExcecaoQuandoFotoNulo() {
        // Arrange
        var itemEntity = new ItemDao();
        itemEntity.setNome("Coca-Cola");
        itemEntity.setDescricao("Refrigerante");
        itemEntity.setPreco(new BigDecimal("20.00"));
        itemEntity.setEntrega(true);

        // Act & Assert
        assertThrows(ItemNonPersistenceException.class, () -> itemCreateAdapter.save(itemEntity));
    }

    @Test
    void deveGerarItemIdAutomaticamente() {
        // Arrange
        var itemEntity = new ItemDao();
        itemEntity.setNome("Coca-Cola");
        itemEntity.setDescricao("Refrigerante");
        itemEntity.setPreco(new BigDecimal("20.00"));
        itemEntity.setEntrega(true);
        itemEntity.setFoto("http://link-foto.com.br");

        // Act
        var savedEntity = itemCreateAdapter.save(itemEntity);

        // Assert
        assertNotNull(savedEntity.getItemId(), "O itemId deve ser gerado automaticamente");
        assertTrue(repository.existsById(savedEntity.getItemId()), "A entidade deve estar persistida no banco");
    }
}

