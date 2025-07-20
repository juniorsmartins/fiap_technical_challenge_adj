package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers.ClienteCreateController;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.InputPresenter;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.in.CreateInputPort;
import br.com.fiap.tech.challenge_user.application.exception.http500.InternalServerProblemException;
import br.com.fiap.tech.challenge_user.domain.entities.Cliente;
import br.com.fiap.tech.challenge_user.domain.entities.Endereco;
import br.com.fiap.tech.challenge_user.application.dtos.in.ClienteDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.in.EnderecoDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.application.dtos.out.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ClienteDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteCreateControllerTest {

    @Mock
    private InputPresenter<ClienteDtoRequest, Cliente> inputPresenter;

    @Mock
    private OutputPresenter<Cliente, ClienteDtoResponse, ClienteDao> outputPresenter;

    @Mock
    private CreateInputPort<Cliente> createInputPort;

    @InjectMocks
    private ClienteCreateController clienteCreateController;

    private ClienteDtoRequest clienteDtoRequest;

    private Cliente cliente;

    private ClienteDtoResponse clienteDtoResponse;

    @BeforeEach
    void setUp() {

        UUID clienteId = UUID.randomUUID();
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
                enderecoId, "01001-000", "Avenida Central", "1500");

        clienteDtoResponse = new ClienteDtoResponse(
                clienteId, "Robert Martin", "martin@email.com", "rmartin", "rmartin!123",
                Date.from(Instant.now()), null, enderecoDtoResponse, "12345-6789-3245");
    }

    @Test
    void deveCriarClienteComSucesso() {
        // Arrange
        doReturn(cliente).when(inputPresenter).toDomainIn(clienteDtoRequest);
        doReturn(cliente).when(createInputPort).create(cliente);
        doReturn(clienteDtoResponse).when(outputPresenter).toDtoResponse(cliente);

        // Act
        ResponseEntity<ClienteDtoResponse> response = clienteCreateController.create(clienteDtoRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(clienteDtoResponse, response.getBody());
        verify(inputPresenter, times(1)).toDomainIn(clienteDtoRequest);
        verify(createInputPort, times(1)).create(cliente);
        verify(outputPresenter, times(1)).toDtoResponse(cliente);
        verifyNoMoreInteractions(inputPresenter, createInputPort, outputPresenter);
    }

    @Test
    void deveLancarInternalServerProblemExceptionQuandoDtoRequestEhNulo() {
        // Act & Assert
        InternalServerProblemException exception = assertThrows(
                InternalServerProblemException.class,
                () -> clienteCreateController.create(null)
        );

        assertEquals("exception.internal.server.error", exception.getMessage());
        verifyNoInteractions(inputPresenter, createInputPort, outputPresenter);
    }
}

