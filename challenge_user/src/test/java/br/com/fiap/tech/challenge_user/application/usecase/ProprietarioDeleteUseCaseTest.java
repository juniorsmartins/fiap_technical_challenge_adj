package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.interfaces.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.ProprietarioNotFoundException;
import br.com.fiap.tech.challenge_user.application.exception.http409.ActiveOwnerBlocksDeletionException;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.RestauranteDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProprietarioDeleteUseCaseTest {

    @Mock
    private FindByIdOutputPort<ProprietarioDao> findByIdOutputPort;

    @Mock
    private DeleteOutputPort<ProprietarioDao> deleteOutputPort;

    @InjectMocks
    private ProprietarioDeleteUseCase proprietarioDeleteUseCase;

    private ProprietarioDao proprietarioEntity;
    private UUID proprietarioId;

    @BeforeEach
    void setUp() {
        proprietarioId = UUID.randomUUID();
        proprietarioEntity = new ProprietarioDao();
        proprietarioEntity.setUsuarioId(proprietarioId);
        proprietarioEntity.setNome("João");
        proprietarioEntity.setEmail("joao@email.com");
        proprietarioEntity.setLogin("joao");
        proprietarioEntity.setRestaurantes(Collections.emptyList());
    }

    @Test
    void deveDeletarProprietarioQuandoEntidadeEhEncontradaESemRestaurantes() {
        // Arrange
        doReturn(Optional.of(proprietarioEntity)).when(findByIdOutputPort).findById(proprietarioId);

        // Act
        proprietarioDeleteUseCase.deleteById(proprietarioId);

        // Assert
        verify(findByIdOutputPort, times(1)).findById(proprietarioId);
        verify(deleteOutputPort, times(1)).delete(proprietarioEntity);
        verifyNoMoreInteractions(findByIdOutputPort, deleteOutputPort);
    }

    @Test
    void deveLancarActiveOwnerBlocksDeletionExceptionQuandoEntidadeTemRestaurantes() {
        // Arrange
        proprietarioEntity.setRestaurantes(List.of(new RestauranteDao()));
        doReturn(Optional.of(proprietarioEntity)).when(findByIdOutputPort).findById(proprietarioId);

        // Act & Assert
        ActiveOwnerBlocksDeletionException exception = assertThrows(
                ActiveOwnerBlocksDeletionException.class,
                () -> proprietarioDeleteUseCase.deleteById(proprietarioId)
        );
        assertEquals(proprietarioId.toString(), exception.getValor());
        verify(findByIdOutputPort, times(1)).findById(proprietarioId);
        verifyNoInteractions(deleteOutputPort);
        verifyNoMoreInteractions(findByIdOutputPort);
    }

    @Test
    void deveLancarProprietarioNotFoundExceptionQuandoEntidadeNaoEhEncontrada() {
        // Arrange
        doReturn(Optional.empty()).when(findByIdOutputPort).findById(proprietarioId);

        // Act & Assert
        ProprietarioNotFoundException exception = assertThrows(
                ProprietarioNotFoundException.class,
                () -> proprietarioDeleteUseCase.deleteById(proprietarioId)
        );
        assertEquals(proprietarioId, exception.getId());
        verify(findByIdOutputPort, times(1)).findById(proprietarioId);
        verifyNoInteractions(deleteOutputPort);
        verifyNoMoreInteractions(findByIdOutputPort);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoIdEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> proprietarioDeleteUseCase.deleteById(null)
        );
        verifyNoInteractions(findByIdOutputPort, deleteOutputPort);
    }
}

