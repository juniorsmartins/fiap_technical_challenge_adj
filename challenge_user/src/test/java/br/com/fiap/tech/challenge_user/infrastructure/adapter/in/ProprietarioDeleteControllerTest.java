package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.application.interfaces.in.DeleteByIdInputPort;
import br.com.fiap.tech.challenge_user.application.exception.http500.InternalServerProblemException;
import br.com.fiap.tech.challenge_user.domain.entities.Proprietario;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers.ProprietarioDeleteController;
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
class ProprietarioDeleteControllerTest {

    @Mock
    private DeleteByIdInputPort<Proprietario> deleteByIdInputPort;

    @InjectMocks
    private ProprietarioDeleteController proprietarioDeleteController;

    private UUID proprietarioId;

    @BeforeEach
    void setUp() {
        proprietarioId = UUID.randomUUID();
    }

    @Test
    void deveDeletarProprietarioComSucesso() {
        // Arrange
        doNothing().when(deleteByIdInputPort).deleteById(proprietarioId);

        // Act
        ResponseEntity<Void> response = proprietarioDeleteController.deleteById(proprietarioId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(deleteByIdInputPort, times(1)).deleteById(proprietarioId);
        verifyNoMoreInteractions(deleteByIdInputPort);
    }

    @Test
    void deveLancarInternalServerProblemExceptionQuandoIdEhNulo() {
        // Act & Assert
        InternalServerProblemException exception = assertThrows(
                InternalServerProblemException.class,
                () -> proprietarioDeleteController.deleteById(null)
        );

        assertEquals("exception.internal.server.error", exception.getMessage());
        verifyNoInteractions(deleteByIdInputPort);
    }
}

