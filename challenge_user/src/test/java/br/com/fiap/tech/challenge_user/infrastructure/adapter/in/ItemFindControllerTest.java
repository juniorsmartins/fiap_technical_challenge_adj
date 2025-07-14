package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers.ItemFindController;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.RecursoNotFoundException;
import br.com.fiap.tech.challenge_user.domain.models.Item;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemFindControllerTest {

    @Mock
    private OutputMapper<Item, ItemDtoResponse, ItemEntity> outputMapper;

    @Mock
    private FindByIdOutputPort<ItemEntity> findByIdOutputPort;

    @InjectMocks
    private ItemFindController itemFindController;

    private UUID itemId;

    private ItemEntity itemEntity;

    private ItemDtoResponse itemDtoResponse;

    @BeforeEach
    void setUp() {
        itemId = UUID.randomUUID();

        itemEntity = new ItemEntity(itemId, "Coca-Cola", "Refrigerante",
                new BigDecimal("20.00"), true, "http://link-foto.com.br");

        itemDtoResponse = new ItemDtoResponse(
                itemId, "Coca-Cola", "Refrigerante", new BigDecimal("20.00"), true,
                "http://link-foto.com.br"
        );
    }

    @Test
    void deveEncontrarItemComSucesso() {
        // Arrange
        doReturn(Optional.of(itemEntity)).when(findByIdOutputPort).findById(itemId);
        doReturn(itemDtoResponse).when(outputMapper).toResponse(itemEntity);

        // Act
        ResponseEntity<ItemDtoResponse> response = itemFindController.findById(itemId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(itemDtoResponse, response.getBody());
        verify(findByIdOutputPort, times(1)).findById(itemId);
        verify(outputMapper, times(1)).toResponse(itemEntity);
        verifyNoMoreInteractions(findByIdOutputPort, outputMapper);
    }

    @Test
    void deveLancarRecursoNotFoundExceptionQuandoItemNaoEncontrado() {
        // Arrange
        doReturn(Optional.empty()).when(findByIdOutputPort).findById(itemId);

        // Act & Assert
        RecursoNotFoundException exception = assertThrows(
                RecursoNotFoundException.class,
                () -> itemFindController.findById(itemId)
        );

        assertEquals(itemId, exception.getId());
        verify(findByIdOutputPort, times(1)).findById(itemId);
        verifyNoInteractions(outputMapper);
        verifyNoMoreInteractions(findByIdOutputPort);
    }
}

