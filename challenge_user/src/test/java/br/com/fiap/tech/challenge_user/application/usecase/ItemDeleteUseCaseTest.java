package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.interfaces.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.RecursoNotFoundException;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ItemDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemDeleteUseCaseTest {

    @Mock
    private FindByIdOutputPort<ItemDao> findByIdOutputPort;

    @Mock
    private DeleteOutputPort<ItemDao> deleteOutputPort;

    @InjectMocks
    private ItemDeleteUseCase itemDeleteUseCase;

    private UUID itemId;

    private ItemDao itemDao;

    @BeforeEach
    void setUp() {
        itemId = UUID.randomUUID();
        itemDao = new ItemDao();
        itemDao.setItemId(itemId);
        itemDao.setNome("Coca-Cola");
        itemDao.setDescricao("Refrigerante");
        itemDao.setPreco(new BigDecimal("20.00"));
        itemDao.setEntrega(true);
        itemDao.setFoto("http://link-foto.com.br");
    }

    @Test
    void deveDeletarItemQuandoEntidadeEhEncontrada() {
        // Arrange
        doReturn(Optional.of(itemDao)).when(findByIdOutputPort).findById(itemId);

        // Act
        itemDeleteUseCase.deleteById(itemId);

        // Assert
        verify(findByIdOutputPort, times(1)).findById(itemId);
        verify(deleteOutputPort, times(1)).delete(itemDao);
        verifyNoMoreInteractions(findByIdOutputPort, deleteOutputPort);
    }

    @Test
    void deveLancarRecursoNotFoundExceptionQuandoEntidadeNaoEhEncontrada() {
        // Arrange
        doReturn(Optional.empty()).when(findByIdOutputPort).findById(itemId);

        // Act & Assert
        RecursoNotFoundException exception = assertThrows(
                RecursoNotFoundException.class,
                () -> itemDeleteUseCase.deleteById(itemId)
        );
        assertEquals(itemId, exception.getId());
        verify(findByIdOutputPort, times(1)).findById(itemId);
        verifyNoInteractions(deleteOutputPort);
        verifyNoMoreInteractions(findByIdOutputPort);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoIdEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> itemDeleteUseCase.deleteById(null)
        );
        verifyNoInteractions(findByIdOutputPort, deleteOutputPort);
    }
}

