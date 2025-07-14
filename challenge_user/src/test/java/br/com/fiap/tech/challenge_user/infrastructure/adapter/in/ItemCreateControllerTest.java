package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers.ItemCreateController;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.InputMapper;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.in.CreateInputPort;
import br.com.fiap.tech.challenge_user.application.exception.http500.InternalServerProblemException;
import br.com.fiap.tech.challenge_user.domain.models.Item;
import br.com.fiap.tech.challenge_user.application.dtos.in.ItemDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.ItemDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ItemEntity;
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
class ItemCreateControllerTest {

    @Mock
    private InputMapper<ItemDtoRequest, Item> inputMapper;

    @Mock
    private OutputMapper<Item, ItemDtoResponse, ItemEntity> outputMapper;

    @Mock
    private CreateInputPort<Item> createInputPort;

    @InjectMocks
    private ItemCreateController itemCreateController;

    private ItemDtoRequest itemDtoRequest;

    private Item item;

    private ItemDtoResponse itemDtoResponse;

    @BeforeEach
    void setUp() {
        var itemId = UUID.randomUUID();

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
    void deveCriarItemComSucesso() {
        // Arrange
        doReturn(item).when(inputMapper).toDomainIn(itemDtoRequest);
        doReturn(item).when(createInputPort).create(item);
        doReturn(itemDtoResponse).when(outputMapper).toDtoResponse(item);

        // Act
        ResponseEntity<ItemDtoResponse> response = itemCreateController.create(itemDtoRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(itemDtoResponse, response.getBody());
        verify(inputMapper, times(1)).toDomainIn(itemDtoRequest);
        verify(createInputPort, times(1)).create(item);
        verify(outputMapper, times(1)).toDtoResponse(item);
        verifyNoMoreInteractions(inputMapper, createInputPort, outputMapper);
    }

    @Test
    void deveLancarInternalServerProblemExceptionQuandoDtoRequestEhNulo() {
        // Act & Assert
        InternalServerProblemException exception = assertThrows(
                InternalServerProblemException.class,
                () -> itemCreateController.create(null)
        );

        assertEquals("exception.internal.server.error", exception.getMessage());
        verifyNoInteractions(inputMapper, createInputPort, outputMapper);
    }
}

