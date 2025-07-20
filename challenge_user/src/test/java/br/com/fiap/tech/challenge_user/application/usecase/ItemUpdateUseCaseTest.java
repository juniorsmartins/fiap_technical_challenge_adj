package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.EntityMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.RecursoNotFoundException;
import br.com.fiap.tech.challenge_user.domain.models.Item;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ItemDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemUpdateUseCaseTest {

    @Mock
    private EntityMapper<Item, ItemDao> entityMapper;

    @Mock
    private CreateOutputPort<ItemDao> createOutputPort;

    @Mock
    private FindByIdOutputPort<ItemDao> findByIdOutputPort;

    @InjectMocks
    private ItemUpdateUseCase itemUpdateUseCase;

    private UUID itemId;

    private Item item;

    private ItemDao itemDao;

    @BeforeEach
    void setUp() {
        itemId = UUID.randomUUID();

        item = new Item(
                itemId, "Coca-Cola", "Refrigerante", new BigDecimal("20.00"), true,
                "http://link-foto.com.br"
        );

        itemDao = new ItemDao(
                itemId, "Pepsi", "Refrigerante", new BigDecimal("18.00"), false,
                "http://link-foto-pepsi.com.br"
        );
    }

    @Test
    void deveAtualizarItemComSucesso() {
        // Arrange
        Item updatedItem = new Item(
                itemId, "Coca-Cola Zero", "Refrigerante sem açúcar", new BigDecimal("22.00"), false,
                "http://link-foto-coca-zero.com.br"
        );

        ItemDao updatedEntity = new ItemDao(
                itemId, "Coca-Cola Zero", "Refrigerante sem açúcar", new BigDecimal("22.00"),
                false, "http://link-foto-coca-zero.com.br"
        );

        doReturn(Optional.of(itemDao)).when(findByIdOutputPort).findById(itemId);
        doReturn(updatedEntity).when(createOutputPort).save(any(ItemDao.class));
        doReturn(updatedItem).when(entityMapper).toDomain(updatedEntity);

        // Act
        Item result = itemUpdateUseCase.update(itemId, updatedItem);

        // Assert
        assertNotNull(result);
        assertEquals(itemId, result.getItemId());
        assertEquals("Coca-Cola Zero", result.getNome());
        assertEquals("Refrigerante sem açúcar", result.getDescricao());
        assertEquals(new BigDecimal("22.00"), result.getPreco());
        assertFalse(result.isEntrega());
        assertEquals("http://link-foto-coca-zero.com.br", result.getFoto());
        verify(findByIdOutputPort, times(1)).findById(itemId);
        verify(createOutputPort, times(1)).save(any(ItemDao.class));
        verify(entityMapper, times(1)).toDomain(updatedEntity);
        verifyNoMoreInteractions(findByIdOutputPort, createOutputPort, entityMapper);
    }

    @Test
    void deveLancarItemNotFoundExceptionQuandoItemNaoEncontrado() {
        // Arrange
        doReturn(Optional.empty()).when(findByIdOutputPort).findById(itemId);

        // Act & Assert
        RecursoNotFoundException exception = assertThrows(
                RecursoNotFoundException.class,
                () -> itemUpdateUseCase.update(itemId, item)
        );

        assertEquals(itemId, exception.getId());
        verify(findByIdOutputPort, times(1)).findById(itemId);
        verifyNoInteractions(createOutputPort, entityMapper);
        verifyNoMoreInteractions(findByIdOutputPort);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoIdEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> itemUpdateUseCase.update(null, item)
        );

        verifyNoInteractions(findByIdOutputPort, createOutputPort, entityMapper);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoItemEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> itemUpdateUseCase.update(itemId, null)
        );

        verifyNoInteractions(findByIdOutputPort, createOutputPort, entityMapper);
    }
}

