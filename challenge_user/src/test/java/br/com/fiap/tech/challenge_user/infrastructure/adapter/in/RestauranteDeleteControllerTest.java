package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.application.port.in.DeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.domain.exception.http500.InternalServerProblemException;
import br.com.fiap.tech.challenge_user.domain.model.Restaurante;
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
class RestauranteDeleteControllerTest {

    @Mock
    private DeleteByIdInputPort<Restaurante> deleteByIdInputPort;

    @InjectMocks
    private RestauranteDeleteController restauranteDeleteController;

    private UUID restauranteId;

    @BeforeEach
    void setUp() {
        restauranteId = UUID.randomUUID();
    }

    @Test
    void deveDeletarRestauranteComSucesso() {
        // Arrange
        doNothing().when(deleteByIdInputPort).deleteById(restauranteId);

        // Act
        ResponseEntity<Void> response = restauranteDeleteController.deleteById(restauranteId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(deleteByIdInputPort, times(1)).deleteById(restauranteId);
        verifyNoMoreInteractions(deleteByIdInputPort);
    }

    @Test
    void deveLancarInternalServerProblemExceptionQuandoIdEhNulo() {
        // Act & Assert
        InternalServerProblemException exception = assertThrows(
                InternalServerProblemException.class,
                () -> restauranteDeleteController.deleteById(null)
        );

        assertEquals("exception.internal.server.error", exception.getMessage());
        verifyNoInteractions(deleteByIdInputPort);
    }
}

