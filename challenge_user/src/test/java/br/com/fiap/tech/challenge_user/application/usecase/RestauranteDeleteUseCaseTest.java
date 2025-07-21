package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.interfaces.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.RecursoNotFoundException;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.RestauranteDao;
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
class RestauranteDeleteUseCaseTest {

    @Mock
    private FindByIdOutputPort<RestauranteDao> findByIdOutputPort;

    @Mock
    private DeleteOutputPort<RestauranteDao> deleteOutputPort;

    @InjectMocks
    private RestauranteDeleteUseCase restauranteDeleteUseCase;

    private RestauranteDao restauranteDao;
    private UUID restauranteId;

    @BeforeEach
    void setUp() {
        restauranteId = UUID.randomUUID();
        restauranteDao = new RestauranteDao();
        restauranteDao.setRestauranteId(restauranteId);
        restauranteDao.setNome("Restaurante Sabor");
        // Outros campos podem ser preenchidos conforme necessário
    }

    @Test
    void deveDeletarRestauranteQuandoEntidadeEhEncontrada() {
        // Arrange
        doReturn(Optional.of(restauranteDao)).when(findByIdOutputPort).findById(restauranteId);

        // Act
        restauranteDeleteUseCase.deleteById(restauranteId);

        // Assert
        verify(findByIdOutputPort, times(1)).findById(restauranteId);
        verify(deleteOutputPort, times(1)).delete(restauranteDao);
        verifyNoMoreInteractions(findByIdOutputPort, deleteOutputPort);
    }

    @Test
    void deveLancarRecursoNotFoundExceptionQuandoEntidadeNaoEhEncontrada() {
        // Arrange
        doReturn(Optional.empty()).when(findByIdOutputPort).findById(restauranteId);

        // Act & Assert
        RecursoNotFoundException exception = assertThrows(
                RecursoNotFoundException.class,
                () -> restauranteDeleteUseCase.deleteById(restauranteId)
        );
        assertEquals(restauranteId, exception.getId());
        verify(findByIdOutputPort, times(1)).findById(restauranteId);
        verifyNoInteractions(deleteOutputPort);
        verifyNoMoreInteractions(findByIdOutputPort);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoIdEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> restauranteDeleteUseCase.deleteById(null)
        );
        verifyNoInteractions(findByIdOutputPort, deleteOutputPort);
    }
}

