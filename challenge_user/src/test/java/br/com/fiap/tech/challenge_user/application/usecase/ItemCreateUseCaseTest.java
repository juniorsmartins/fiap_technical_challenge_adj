package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.DaoPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.domain.entities.Item;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ItemDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemCreateUseCaseTest {

    @Mock
    private DaoPresenter<Item, ItemDao> daoPresenter;

    @Mock
    private CreateOutputPort<ItemDao> createOutputPort;

    @InjectMocks
    private ItemCreateUseCase itemCreateUseCase;

    private Item item;

    private ItemDao itemDao;

    @BeforeEach
    void setUp() {
        var itemId = UUID.randomUUID();

        item = new Item(
                itemId, "Coca-Cola", "Refrigerante", new BigDecimal("20.00"), true,
                "http://link-foto.com.br"
        );

        itemDao = new ItemDao(
                itemId, "Coca-Cola", "Refrigerante", new BigDecimal("20.00"), true,
                "http://link-foto.com.br"
        );
    }

    @Test
    void deveCriarItemComSucesso() {
        // Arrange
        doReturn(itemDao).when(daoPresenter).toEntity(item);
        doReturn(itemDao).when(createOutputPort).save(itemDao);
        doReturn(item).when(daoPresenter).toDomain(itemDao);

        // Act
        Item result = itemCreateUseCase.create(item);

        // Assert
        assertNotNull(result);
        assertEquals(item.getItemId(), result.getItemId());
        assertEquals("Coca-Cola", result.getNome());
        assertEquals("Refrigerante", result.getDescricao());
        assertEquals(new BigDecimal("20.00"), result.getPreco());
        assertTrue(result.isEntrega());
        assertEquals("http://link-foto.com.br", result.getFoto());
        verify(daoPresenter, times(1)).toEntity(item);
        verify(createOutputPort, times(1)).save(itemDao);
        verify(daoPresenter, times(1)).toDomain(itemDao);
        verifyNoMoreInteractions(daoPresenter, createOutputPort);
    }
}

