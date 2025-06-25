package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.application.mapper.InputMapper;
import br.com.fiap.tech.challenge_user.application.mapper.OutputMapper;
import br.com.fiap.tech.challenge_user.application.port.in.UpdateInputPort;
import br.com.fiap.tech.challenge_user.domain.exception.http404.RecursoNotFoundException;
import br.com.fiap.tech.challenge_user.domain.exception.http500.InternalServerProblemException;
import br.com.fiap.tech.challenge_user.domain.model.Item;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.ItemDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.ItemDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemUpdateControllerTest {

    @Mock
    private InputMapper<ItemDtoRequest, Item> inputMapper;

    @Mock
    private OutputMapper<Item, ItemDtoResponse, ItemEntity> outputMapper;

    @Mock
    private UpdateInputPort<Item> updateInputPort;

    @InjectMocks
    private ItemUpdateController itemUpdateController;

    private UUID itemId;

    private ItemDtoRequest itemDtoRequest;

    private Item item;

    private ItemDtoResponse itemDtoResponse;

    @BeforeEach
    void setUp() {
        itemId = UUID.randomUUID();

        itemDtoRequest = new ItemDtoRequest(
                "Coca-Cola", "Refrigerante", new BigDecimal("20.00"), true,
                "http://link-foto.com.br"
        );

        item = new Item(
                itemId, "Coca-Cola", "Refrigerante", new BigDecimal("20.00"), true,
                "http://link-foto.com.br"
        );

        itemDtoResponse = new ItemDtoResponse(
                itemId, "Coca-Cola", "Refrigerante", new BigDecimal("20.00"), true,
                "http://link-foto.com.br"
        );
    }

    @Test
    void deveAtualizarItemComSucesso() {
        // Arrange
        doReturn(item).when(inputMapper).toDomainIn(itemDtoRequest);
        doReturn(item).when(updateInputPort).update(itemId, item);
        doReturn(itemDtoResponse).when(outputMapper).toDtoResponse(item);

        // Act
        ResponseEntity<ItemDtoResponse> response = itemUpdateController.update(itemId, itemDtoRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(itemDtoResponse, response.getBody());
        verify(inputMapper, times(1)).toDomainIn(itemDtoRequest);
        verify(updateInputPort, times(1)).update(itemId, item);
        verify(outputMapper, times(1)).toDtoResponse(item);
        verifyNoMoreInteractions(inputMapper, updateInputPort, outputMapper);
    }

    @Test
    void deveLancarInternalServerProblemExceptionQuandoDtoRequestEhNulo() {
        // Act & Assert
        InternalServerProblemException exception = assertThrows(
                InternalServerProblemException.class,
                () -> itemUpdateController.update(itemId, null)
        );

        assertEquals("exception.internal.server.error", exception.getMessage());
        verifyNoInteractions(inputMapper, updateInputPort, outputMapper);
    }

    @Test
    void deveLancarRecursoNotFoundExceptionQuandoItemNaoEncontrado() {
        // Arrange
        doReturn(item).when(inputMapper).toDomainIn(itemDtoRequest);
        doThrow(new RecursoNotFoundException(itemId)).when(updateInputPort).update(itemId, item);

        // Act & Assert
        RecursoNotFoundException exception = assertThrows(
                RecursoNotFoundException.class,
                () -> itemUpdateController.update(itemId, itemDtoRequest)
        );

        assertEquals(itemId, exception.getId());
        verify(inputMapper, times(1)).toDomainIn(itemDtoRequest);
        verify(updateInputPort, times(1)).update(itemId, item);
        verifyNoInteractions(outputMapper);
        verifyNoMoreInteractions(inputMapper, updateInputPort);
    }
}

