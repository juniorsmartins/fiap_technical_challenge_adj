package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers.ClienteFindController;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.RecursoNotFoundException;
import br.com.fiap.tech.challenge_user.domain.models.Cliente;
import br.com.fiap.tech.challenge_user.application.dtos.out.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.application.dtos.out.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ClienteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteFindControllerTest {

    @Mock
    private OutputMapper<Cliente, ClienteDtoResponse, ClienteEntity> outputMapper;

    @Mock
    private FindByIdOutputPort<ClienteEntity> findByIdOutputPort;

    @InjectMocks
    private ClienteFindController clienteFindController;

    private UUID clienteId;

    private ClienteEntity clienteEntity;

    private ClienteDtoResponse clienteDtoResponse;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();
        var enderecoId = UUID.randomUUID();

        clienteEntity = new ClienteEntity();
        clienteEntity.setUsuarioId(clienteId);
        clienteEntity.setNome("Robert Martin");
        clienteEntity.setEmail("martin@email.com");
        clienteEntity.setLogin("rmartin");
        clienteEntity.setSenha("rmartin!123");
        clienteEntity.setNumeroCartaoFidelidade("12345-6789-3245");

        var enderecoDtoResponse = new EnderecoDtoResponse(
                enderecoId, "01001-000", "Avenida Central", "1500"
        );

        clienteDtoResponse = new ClienteDtoResponse(
                clienteId, "Robert Martin","martin@email.com", "rmartin", "rmartin!123",
                null, null, enderecoDtoResponse, "12345-6789-3245"
        );
    }

    @Test
    void deveEncontrarClienteComSucesso() {
        // Arrange
        doReturn(Optional.of(clienteEntity)).when(findByIdOutputPort).findById(clienteId);
        doReturn(clienteDtoResponse).when(outputMapper).toResponse(clienteEntity);

        // Act
        ResponseEntity<ClienteDtoResponse> response = clienteFindController.findById(clienteId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clienteDtoResponse, response.getBody());
        verify(findByIdOutputPort, times(1)).findById(clienteId);
        verify(outputMapper, times(1)).toResponse(clienteEntity);
        verifyNoMoreInteractions(findByIdOutputPort, outputMapper);
    }

    @Test
    void deveLancarRecursoNotFoundExceptionQuandoClienteNaoEncontrado() {
        // Arrange
        doReturn(Optional.empty()).when(findByIdOutputPort).findById(clienteId);

        // Act & Assert
        RecursoNotFoundException exception = assertThrows(
                RecursoNotFoundException.class,
                () -> clienteFindController.findById(clienteId)
        );

        assertEquals(clienteId, exception.getId());
        verify(findByIdOutputPort, times(1)).findById(clienteId);
        verifyNoInteractions(outputMapper);
        verifyNoMoreInteractions(findByIdOutputPort);
    }
}

