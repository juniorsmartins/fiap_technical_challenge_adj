package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers.ProprietarioUpdateController;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.InputPresenter;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.in.UpdateInputPort;
import br.com.fiap.tech.challenge_user.application.exception.http500.InternalServerProblemException;
import br.com.fiap.tech.challenge_user.domain.entities.Endereco;
import br.com.fiap.tech.challenge_user.domain.entities.Proprietario;
import br.com.fiap.tech.challenge_user.application.dtos.in.EnderecoDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.in.ProprietarioDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.application.dtos.out.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
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
class ProprietarioUpdateControllerTest {

    @Mock
    private InputPresenter<ProprietarioDtoRequest, Proprietario> inputPresenter;

    @Mock
    private OutputPresenter<Proprietario, ProprietarioDtoResponse, ProprietarioDao> outputPresenter;

    @Mock
    private UpdateInputPort<Proprietario> updateInputPort;

    @InjectMocks
    private ProprietarioUpdateController proprietarioUpdateController;

    private ProprietarioDtoRequest proprietarioDtoRequest;

    private Proprietario proprietario;

    private ProprietarioDtoResponse proprietarioDtoResponse;

    private UUID proprietarioId;

    @BeforeEach
    void setUp() {
        proprietarioId = UUID.randomUUID();
        var enderecoId = UUID.randomUUID();

        var enderecoDtoRequest = new EnderecoDtoRequest("01001-000", "Avenida Central", "1500");

        proprietarioDtoRequest = new ProprietarioDtoRequest(
                "João Silva", "joao@email.com", "jsilva", "jsilva!123",
                enderecoDtoRequest, "Proprietário principal"
        );

        var endereco = new Endereco();
        endereco.setCep("01001-000");
        endereco.setLogradouro("Avenida Central");
        endereco.setNumero("1500");

        proprietario = new Proprietario(
                proprietarioId, "João Silva", "joao@email.com", "jsilva", "jsilva!123",
                endereco, "Proprietário principal"
        );

        var enderecoDtoResponse = new EnderecoDtoResponse(
                enderecoId, "01001-000", "Avenida Central", "1500"
        );

        proprietarioDtoResponse = new ProprietarioDtoResponse(
                proprietarioId, "João Silva", "joao@email.com", "jsilva", "jsilva!123",
                null, null, enderecoDtoResponse, "Proprietário principal"
        );
    }

    @Test
    void deveAtualizarProprietarioComSucesso() {
        // Arrange
        doReturn(proprietario).when(inputPresenter).toDomainIn(proprietarioDtoRequest);
        doReturn(proprietario).when(updateInputPort).update(proprietarioId, proprietario);
        doReturn(proprietarioDtoResponse).when(outputPresenter).toDtoResponse(proprietario);

        // Act
        ResponseEntity<ProprietarioDtoResponse> response = proprietarioUpdateController.update(proprietarioId, proprietarioDtoRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(proprietarioDtoResponse, response.getBody());
        verify(inputPresenter, times(1)).toDomainIn(proprietarioDtoRequest);
        verify(updateInputPort, times(1)).update(proprietarioId, proprietario);
        verify(outputPresenter, times(1)).toDtoResponse(proprietario);
        verifyNoMoreInteractions(inputPresenter, updateInputPort, outputPresenter);
    }

    @Test
    void deveLancarInternalServerProblemExceptionQuandoDtoRequestEhNulo() {
        // Act & Assert
        InternalServerProblemException exception = assertThrows(
                InternalServerProblemException.class,
                () -> proprietarioUpdateController.update(proprietarioId, null)
        );

        assertEquals("exception.internal.server.error", exception.getMessage());
        verifyNoInteractions(inputPresenter, updateInputPort, outputPresenter);
    }
}

