package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.interfaces.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.RecursoNotFoundException;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteDeleteUseCaseTest {

    @Mock
    private FindByIdOutputPort<ClienteEntity> findByIdOutputPort;

    @Mock
    private DeleteOutputPort<ClienteEntity> deleteOutputPort;

    @InjectMocks
    private ClienteDeleteUseCase clienteDeleteUseCase;

    private ClienteEntity clienteEntity;
    private UUID clienteId;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();
        clienteEntity = new ClienteEntity();
        clienteEntity.setUsuarioId(clienteId);
        clienteEntity.setNome("Maria");
        clienteEntity.setEmail("maria@email.com");
        clienteEntity.setLogin("maria");
    }

    @Test
    void deveDeletarClienteQuandoEntidadeEhEncontrada() {
        // Arrange
        doReturn(Optional.of(clienteEntity)).when(findByIdOutputPort).findById(clienteId);

        // Act
        clienteDeleteUseCase.deleteById(clienteId);

        // Assert
        verify(findByIdOutputPort, times(1)).findById(clienteId);
        verify(deleteOutputPort, times(1)).delete(clienteEntity);
        verifyNoMoreInteractions(findByIdOutputPort, deleteOutputPort);
    }

    @Test
    void deveLancarRecursoNotFoundExceptionQuandoEntidadeNaoEhEncontrada() {
        // Arrange
        doReturn(Optional.empty()).when(findByIdOutputPort).findById(clienteId);

        // Act & Assert
        RecursoNotFoundException exception = assertThrows(
                RecursoNotFoundException.class,
                () -> clienteDeleteUseCase.deleteById(clienteId)
        );
        assertEquals(clienteId, exception.getId());
        verify(findByIdOutputPort, times(1)).findById(clienteId);
        verifyNoInteractions(deleteOutputPort);
        verifyNoMoreInteractions(findByIdOutputPort);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoIdEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> clienteDeleteUseCase.deleteById(null)
        );
        verifyNoInteractions(findByIdOutputPort, deleteOutputPort);
    }
}

