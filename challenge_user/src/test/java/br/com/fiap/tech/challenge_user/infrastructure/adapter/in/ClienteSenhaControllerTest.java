package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.application.port.in.UsuarioSenhaInputPort;
import br.com.fiap.tech.challenge_user.domain.exception.http500.InternalServerProblemException;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.SenhaDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
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
class ClienteSenhaControllerTest {

    @Mock
    private UsuarioSenhaInputPort<ClienteEntity> senhaInputPort;

    @InjectMocks
    private ClienteSenhaController clienteSenhaController;

    private SenhaDtoRequest senhaDtoRequest;

    private UUID usuarioId;

    @BeforeEach
    void setUp() {
        usuarioId = UUID.randomUUID();

        senhaDtoRequest = new SenhaDtoRequest(
                usuarioId, "senhaAntiga123", "senhaNova123"
        );
    }

    @Test
    void deveAtualizarSenhaComSucesso() {
        // Arrange
        doNothing().when(senhaInputPort).updatePassword(usuarioId, "senhaAntiga123", "senhaNova123");

        // Act
        ResponseEntity<Void> response = clienteSenhaController.updatePassword(senhaDtoRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(senhaInputPort, times(1)).updatePassword(usuarioId, "senhaAntiga123", "senhaNova123");
        verifyNoMoreInteractions(senhaInputPort);
    }

    @Test
    void deveLancarInternalServerProblemExceptionQuandoSenhaDtoRequestEhNulo() {
        // Act & Assert
        InternalServerProblemException exception = assertThrows(
                InternalServerProblemException.class,
                () -> clienteSenhaController.updatePassword(null)
        );
        assertEquals("exception.internal.server.error", exception.getMessage());
        verifyNoInteractions(senhaInputPort);
    }
}

