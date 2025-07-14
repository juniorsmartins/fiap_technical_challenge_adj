package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.presenters.OutputMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.RecursoNotFoundException;
import br.com.fiap.tech.challenge_user.domain.model.Proprietario;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProprietarioFindControllerTest {

    @Mock
    private OutputMapper<Proprietario, ProprietarioDtoResponse, ProprietarioEntity> outputMapper;

    @Mock
    private FindByIdOutputPort<ProprietarioEntity> findByIdOutputPort;

    @InjectMocks
    private ProprietarioFindController proprietarioFindController;

    private UUID proprietarioId;

    private ProprietarioEntity proprietarioEntity;

    private ProprietarioDtoResponse proprietarioDtoResponse;

    @BeforeEach
    void setUp() {
        proprietarioId = UUID.randomUUID();
        var enderecoId = UUID.randomUUID();

        proprietarioEntity = new ProprietarioEntity();
        proprietarioEntity.setUsuarioId(proprietarioId);
        proprietarioEntity.setNome("João Silva");
        proprietarioEntity.setEmail("joao@email.com");
        proprietarioEntity.setLogin("jsilva");
        proprietarioEntity.setSenha("jsilva!123");
        proprietarioEntity.setDescricao("Proprietário principal");

        var enderecoDtoResponse = new EnderecoDtoResponse(
                enderecoId, "01001-000", "Avenida Central", "1500"
        );

        proprietarioDtoResponse = new ProprietarioDtoResponse(
                proprietarioId, "João Silva", "joao@email.com", "jsilva", "jsilva!123",
                Date.from(Instant.now()), null, enderecoDtoResponse, "Teste"
        );
    }

    @Test
    void deveEncontrarProprietarioComSucesso() {
        // Arrange
        doReturn(Optional.of(proprietarioEntity)).when(findByIdOutputPort).findById(proprietarioId);
        doReturn(proprietarioDtoResponse).when(outputMapper).toResponse(proprietarioEntity);

        // Act
        ResponseEntity<ProprietarioDtoResponse> response = proprietarioFindController.findById(proprietarioId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(proprietarioDtoResponse, response.getBody());
        verify(findByIdOutputPort, times(1)).findById(proprietarioId);
        verify(outputMapper, times(1)).toResponse(proprietarioEntity);
        verifyNoMoreInteractions(findByIdOutputPort, outputMapper);
    }

    @Test
    void deveLancarRecursoNotFoundExceptionQuandoProprietarioNaoEncontrado() {
        // Arrange
        doReturn(Optional.empty()).when(findByIdOutputPort).findById(proprietarioId);

        // Act & Assert
        RecursoNotFoundException exception = assertThrows(
                RecursoNotFoundException.class,
                () -> proprietarioFindController.findById(proprietarioId)
        );

        assertEquals(proprietarioId, exception.getId());
        verify(findByIdOutputPort, times(1)).findById(proprietarioId);
        verifyNoInteractions(outputMapper);
        verifyNoMoreInteractions(findByIdOutputPort);
    }
}

