package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.interfaces.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.RecursoNotFoundException;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ClienteDao;
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
    private FindByIdOutputPort<ClienteDao> findByIdOutputPort;

    @Mock
    private DeleteOutputPort<ClienteDao> deleteOutputPort;

    @InjectMocks
    private ClienteDeleteUseCase clienteDeleteUseCase;

    private ClienteDao clienteDao;
    private UUID clienteId;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();
        clienteDao = new ClienteDao();
        clienteDao.setUsuarioId(clienteId);
        clienteDao.setNome("Maria");
        clienteDao.setEmail("maria@email.com");
        clienteDao.setLogin("maria");
    }

    @Test
    void deveDeletarClienteQuandoEntidadeEhEncontrada() {
        // Arrange
        doReturn(Optional.of(clienteDao)).when(findByIdOutputPort).findById(clienteId);

        // Act
        clienteDeleteUseCase.deleteById(clienteId);

        // Assert
        verify(findByIdOutputPort, times(1)).findById(clienteId);
        verify(deleteOutputPort, times(1)).delete(clienteDao);
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

