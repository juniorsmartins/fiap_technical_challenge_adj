package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.UsuarioNotFoundException;
import br.com.fiap.tech.challenge_user.application.exception.http409.IncompatibleOldPasswordException;
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
class ClienteSenhaUseCaseTest {

    @Mock
    private FindByIdOutputPort<ClienteDao> findByIdOutputPort;

    @Mock
    private CreateOutputPort<ClienteDao> createOutputPort;

    @InjectMocks
    private ClienteSenhaUseCase clienteSenhaUseCase;

    private ClienteDao clienteDao;
    private UUID usuarioId;
    private String senhaAntiga;
    private String senhaNova;

    @BeforeEach
    void setUp() {
        usuarioId = UUID.randomUUID();
        senhaAntiga = "senha123";
        senhaNova = "novaSenha456";
        clienteDao = new ClienteDao();
        clienteDao.setUsuarioId(usuarioId);
        clienteDao.setNome("Maria");
        clienteDao.setEmail("maria@email.com");
        clienteDao.setLogin("maria");
        clienteDao.setSenha(senhaAntiga);
    }

    @Test
    void deveAtualizarSenhaQuandoDadosSaoValidos() {
        // Arrange
        doReturn(Optional.of(clienteDao)).when(findByIdOutputPort).findById(usuarioId);
        doReturn(clienteDao).when(createOutputPort).save(clienteDao);

        // Act
        clienteSenhaUseCase.updatePassword(usuarioId, senhaAntiga, senhaNova);

        // Assert
        assertEquals(senhaNova, clienteDao.getSenha());
        verify(findByIdOutputPort, times(1)).findById(usuarioId);
        verify(createOutputPort, times(1)).save(clienteDao);
        verifyNoMoreInteractions(findByIdOutputPort, createOutputPort);
    }

    @Test
    void deveLancarIncompatibleOldPasswordExceptionQuandoSenhaAntigaEhInvalida() {
        // Arrange
        String senhaAntigaInvalida = "senhaErrada";
        doReturn(Optional.of(clienteDao)).when(findByIdOutputPort).findById(usuarioId);

        // Act & Assert
        IncompatibleOldPasswordException exception = assertThrows(
                IncompatibleOldPasswordException.class,
                () -> clienteSenhaUseCase.updatePassword(usuarioId, senhaAntigaInvalida, senhaNova)
        );
        assertEquals(senhaAntigaInvalida, exception.getValor());
        verify(findByIdOutputPort, times(1)).findById(usuarioId);
        verifyNoInteractions(createOutputPort);
        verifyNoMoreInteractions(findByIdOutputPort);
    }

    @Test
    void deveLancarUsuarioNotFoundExceptionQuandoUsuarioNaoEhEncontrado() {
        // Arrange
        doReturn(Optional.empty()).when(findByIdOutputPort).findById(usuarioId);

        // Act & Assert
        UsuarioNotFoundException exception = assertThrows(
                UsuarioNotFoundException.class,
                () -> clienteSenhaUseCase.updatePassword(usuarioId, senhaAntiga, senhaNova)
        );
        assertEquals(usuarioId, exception.getId());
        verify(findByIdOutputPort, times(1)).findById(usuarioId);
        verifyNoInteractions(createOutputPort);
        verifyNoMoreInteractions(findByIdOutputPort);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoUsuarioIdEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> clienteSenhaUseCase.updatePassword(null, senhaAntiga, senhaNova)
        );
        verifyNoInteractions(findByIdOutputPort, createOutputPort);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoSenhaAntigaEhNula() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> clienteSenhaUseCase.updatePassword(usuarioId, null, senhaNova)
        );
        verifyNoInteractions(findByIdOutputPort, createOutputPort);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoSenhaNovaEhNula() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> clienteSenhaUseCase.updatePassword(usuarioId, senhaAntiga, null)
        );
        verifyNoInteractions(findByIdOutputPort, createOutputPort);
    }
}

