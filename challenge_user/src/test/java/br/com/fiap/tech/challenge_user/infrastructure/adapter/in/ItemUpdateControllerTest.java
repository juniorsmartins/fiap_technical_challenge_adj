package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers.ItemUpdateController;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.InputPresenter;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.in.UpdateInputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.RecursoNotFoundException;
import br.com.fiap.tech.challenge_user.application.exception.http500.InternalServerProblemException;
import br.com.fiap.tech.challenge_user.domain.entities.Item;
import br.com.fiap.tech.challenge_user.application.dtos.in.ItemDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.ItemDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ItemDao;
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
    private InputPresenter<ItemDtoRequest, Item> inputPresenter;

    @Mock
    private OutputPresenter<Item, ItemDtoResponse, ItemDao> outputPresenter;

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
        doReturn(item).when(inputPresenter).toDomainIn(itemDtoRequest);
        doReturn(item).when(updateInputPort).update(itemId, item);
        doReturn(itemDtoResponse).when(outputPresenter).toDtoResponse(item);

        // Act
        ResponseEntity<ItemDtoResponse> response = itemUpdateController.update(itemId, itemDtoRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(itemDtoResponse, response.getBody());
        verify(inputPresenter, times(1)).toDomainIn(itemDtoRequest);
        verify(updateInputPort, times(1)).update(itemId, item);
        verify(outputPresenter, times(1)).toDtoResponse(item);
        verifyNoMoreInteractions(inputPresenter, updateInputPort, outputPresenter);
    }

    @Test
    void deveLancarInternalServerProblemExceptionQuandoDtoRequestEhNulo() {
        // Act & Assert
        InternalServerProblemException exception = assertThrows(
                InternalServerProblemException.class,
                () -> itemUpdateController.update(itemId, null)
        );

        assertEquals("exception.internal.server.error", exception.getMessage());
        verifyNoInteractions(inputPresenter, updateInputPort, outputPresenter);
    }

    @Test
    void deveLancarRecursoNotFoundExceptionQuandoItemNaoEncontrado() {
        // Arrange
        doReturn(item).when(inputPresenter).toDomainIn(itemDtoRequest);
        doThrow(new RecursoNotFoundException(itemId)).when(updateInputPort).update(itemId, item);

        // Act & Assert
        RecursoNotFoundException exception = assertThrows(
                RecursoNotFoundException.class,
                () -> itemUpdateController.update(itemId, itemDtoRequest)
        );

        assertEquals(itemId, exception.getId());
        verify(inputPresenter, times(1)).toDomainIn(itemDtoRequest);
        verify(updateInputPort, times(1)).update(itemId, item);
        verifyNoInteractions(outputPresenter);
        verifyNoMoreInteractions(inputPresenter, updateInputPort);
    }
}

