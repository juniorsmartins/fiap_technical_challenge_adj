package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.presenters.InputMapper;
import br.com.fiap.tech.challenge_user.infrastructure.presenters.OutputMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.in.UpdateInputPort;
import br.com.fiap.tech.challenge_user.application.exception.http500.InternalServerProblemException;
import br.com.fiap.tech.challenge_user.domain.model.Cliente;
import br.com.fiap.tech.challenge_user.domain.model.Endereco;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.ClienteDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.EnderecoDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.EnderecoDtoResponse;
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
class ClienteUpdateControllerTest {

    @Mock
    private InputMapper<ClienteDtoRequest, Cliente> inputMapper;

    @Mock
    private OutputMapper<Cliente, ClienteDtoResponse, ClienteEntity> outputMapper;

    @Mock
    private UpdateInputPort<Cliente> updateInputPort;

    @InjectMocks
    private ClienteUpdateController clienteUpdateController;

    private ClienteDtoRequest clienteDtoRequest;

    private Cliente cliente;

    private ClienteDtoResponse clienteDtoResponse;

    private UUID clienteId;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();
        var enderecoId = UUID.randomUUID();

        var enderecoDtoRequest = new EnderecoDtoRequest("01001-000", "Avenida Central", "1500");

        clienteDtoRequest = new ClienteDtoRequest(
                "Robert Martin", "martin@email.com", "rmartin", "rmartin!123",
                enderecoDtoRequest, "12345-6789-3245"
        );

        var endereco = new Endereco();
        endereco.setCep("01001-000");
        endereco.setLogradouro("Avenida Central");
        endereco.setNumero("1500");

        cliente = new Cliente(
                clienteId, "Robert Martin", "martin@email.com", "rmartin", "rmartin!123",
                endereco, "12345-6789-3245");

        var enderecoDtoResponse = new EnderecoDtoResponse(
                enderecoId, "01001-000", "Avenida Central", "1500"
        );

        clienteDtoResponse = new ClienteDtoResponse(
                clienteId, "Robert Martin","martin@email.com", "rmartin", "rmartin!123",
                null, null, enderecoDtoResponse, "12345-6789-3245"
        );
    }

    @Test
    void deveAtualizarClienteComSucesso() {
        // Arrange
        doReturn(cliente).when(inputMapper).toDomainIn(clienteDtoRequest);
        doReturn(cliente).when(updateInputPort).update(clienteId, cliente);
        doReturn(clienteDtoResponse).when(outputMapper).toDtoResponse(cliente);

        // Act
        ResponseEntity<ClienteDtoResponse> response = clienteUpdateController.update(clienteId, clienteDtoRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clienteDtoResponse, response.getBody());
        verify(inputMapper, times(1)).toDomainIn(clienteDtoRequest);
        verify(updateInputPort, times(1)).update(clienteId, cliente);
        verify(outputMapper, times(1)).toDtoResponse(cliente);
        verifyNoMoreInteractions(inputMapper, updateInputPort, outputMapper);
    }

    @Test
    void deveLancarInternalServerProblemExceptionQuandoDtoRequestEhNulo() {
        // Act & Assert
        InternalServerProblemException exception = assertThrows(
                InternalServerProblemException.class,
                () -> clienteUpdateController.update(clienteId, null)
        );
        assertEquals("exception.internal.server.error", exception.getMessage());
        verifyNoInteractions(inputMapper, updateInputPort, outputMapper);
    }
}

