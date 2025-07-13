package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.presenters.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.domain.model.Item;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ItemEntity;
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
    private EntityMapper<Item, ItemEntity> entityMapper;

    @Mock
    private CreateOutputPort<ItemEntity> createOutputPort;

    @InjectMocks
    private ItemCreateUseCase itemCreateUseCase;

    private Item item;

    private ItemEntity itemEntity;

    @BeforeEach
    void setUp() {
        var itemId = UUID.randomUUID();

        item = new Item(
                itemId, "Coca-Cola", "Refrigerante", new BigDecimal("20.00"), true,
                "http://link-foto.com.br"
        );

        itemEntity = new ItemEntity(
                itemId, "Coca-Cola", "Refrigerante", new BigDecimal("20.00"), true,
                "http://link-foto.com.br"
        );
    }

    @Test
    void deveCriarItemComSucesso() {
        // Arrange
        doReturn(itemEntity).when(entityMapper).toEntity(item);
        doReturn(itemEntity).when(createOutputPort).save(itemEntity);
        doReturn(item).when(entityMapper).toDomain(itemEntity);

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
        verify(entityMapper, times(1)).toEntity(item);
        verify(createOutputPort, times(1)).save(itemEntity);
        verify(entityMapper, times(1)).toDomain(itemEntity);
        verifyNoMoreInteractions(entityMapper, createOutputPort);
    }
}

