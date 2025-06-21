package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.application.port.in.DeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.domain.exception.http500.InternalServerProblemException;
import br.com.fiap.tech.challenge_user.domain.model.Cliente;
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
class ClienteDeleteControllerTest {

    @Mock
    private DeleteByIdInputPort<Cliente> deleteByIdInputPort;

    @InjectMocks
    private ClienteDeleteController clienteDeleteController;

    private UUID clienteId;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();
    }

    @Test
    void deveDeletarClienteComSucesso() {
        // Arrange
        doNothing().when(deleteByIdInputPort).deleteById(clienteId);

        // Act
        ResponseEntity<Void> response = clienteDeleteController.deleteById(clienteId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(deleteByIdInputPort, times(1)).deleteById(clienteId);
        verifyNoMoreInteractions(deleteByIdInputPort);
    }

    @Test
    void deveLancarInternalServerProblemExceptionQuandoIdEhNulo() {
        // Act & Assert
        InternalServerProblemException exception = assertThrows(
                InternalServerProblemException.class,
                () -> clienteDeleteController.deleteById(null)
        );
        assertEquals("exception.internal.server.error", exception.getMessage());
        verifyNoInteractions(deleteByIdInputPort);
    }
}

