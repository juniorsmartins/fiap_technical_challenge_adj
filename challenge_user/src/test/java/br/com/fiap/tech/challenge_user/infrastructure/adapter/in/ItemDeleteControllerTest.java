package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.application.port.in.DeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.domain.exception.http500.InternalServerProblemException;
import br.com.fiap.tech.challenge_user.domain.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemDeleteControllerTest {

    @Mock
    private DeleteByIdInputPort<Item> deleteByIdInputPort;

    @InjectMocks
    private ItemDeleteController itemDeleteController;

    private UUID itemId;

    @BeforeEach
    void setUp() {
        itemId = UUID.randomUUID();
    }

    @Test
    void deveDeletarItemComSucesso() {
        // Arrange
        doNothing().when(deleteByIdInputPort).deleteById(itemId);

        // Act
        ResponseEntity<Void> response = itemDeleteController.deleteById(itemId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(deleteByIdInputPort, times(1)).deleteById(itemId);
        verifyNoMoreInteractions(deleteByIdInputPort);
    }

    @Test
    void deveLancarInternalServerProblemExceptionQuandoIdEhNulo() {
        // Act & Assert
        InternalServerProblemException exception = assertThrows(
                InternalServerProblemException.class,
                () -> itemDeleteController.deleteById(null)
        );
        assertEquals("exception.internal.server.error", exception.getMessage());
        verifyNoInteractions(deleteByIdInputPort);
    }
}

