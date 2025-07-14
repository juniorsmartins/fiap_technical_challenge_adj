package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.UsuarioNotFoundException;
import br.com.fiap.tech.challenge_user.application.exception.http409.IncompatibleOldPasswordException;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
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
class ProprietarioSenhaUseCaseTest {

    @Mock
    private FindByIdOutputPort<ProprietarioEntity> findByIdOutputPort;

    @Mock
    private CreateOutputPort<ProprietarioEntity> createOutputPort;

    @InjectMocks
    private ProprietarioSenhaUseCase proprietarioSenhaUseCase;

    private ProprietarioEntity proprietarioEntity;
    private UUID usuarioId;
    private String senhaAntiga;
    private String senhaNova;

    @BeforeEach
    void setUp() {
        usuarioId = UUID.randomUUID();
        senhaAntiga = "senha123";
        senhaNova = "novaSenha456";
        proprietarioEntity = new ProprietarioEntity();
        proprietarioEntity.setUsuarioId(usuarioId);
        proprietarioEntity.setNome("João");
        proprietarioEntity.setEmail("joao@email.com");
        proprietarioEntity.setLogin("joao");
        proprietarioEntity.setSenha(senhaAntiga);
    }

    @Test
    void deveAtualizarSenhaQuandoDadosSaoValidos() {
        // Arrange
        doReturn(Optional.of(proprietarioEntity)).when(findByIdOutputPort).findById(usuarioId);
        doReturn(proprietarioEntity).when(createOutputPort).save(proprietarioEntity);

        // Act
        proprietarioSenhaUseCase.updatePassword(usuarioId, senhaAntiga, senhaNova);

        // Assert
        assertEquals(senhaNova, proprietarioEntity.getSenha());
        verify(findByIdOutputPort, times(1)).findById(usuarioId);
        verify(createOutputPort, times(1)).save(proprietarioEntity);
        verifyNoMoreInteractions(findByIdOutputPort, createOutputPort);
    }

    @Test
    void deveLancarIncompatibleOldPasswordExceptionQuandoSenhaAntigaEhInvalida() {
        // Arrange
        String senhaAntigaInvalida = "senhaErrada";
        doReturn(Optional.of(proprietarioEntity)).when(findByIdOutputPort).findById(usuarioId);

        // Act & Assert
        IncompatibleOldPasswordException exception = assertThrows(
                IncompatibleOldPasswordException.class,
                () -> proprietarioSenhaUseCase.updatePassword(usuarioId, senhaAntigaInvalida, senhaNova)
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
                () -> proprietarioSenhaUseCase.updatePassword(usuarioId, senhaAntiga, senhaNova)
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
                () -> proprietarioSenhaUseCase.updatePassword(null, senhaAntiga, senhaNova)
        );
        verifyNoInteractions(findByIdOutputPort, createOutputPort);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoSenhaAntigaEhNula() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> proprietarioSenhaUseCase.updatePassword(usuarioId, null, senhaNova)
        );
        verifyNoInteractions(findByIdOutputPort, createOutputPort);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoSenhaNovaEhNula() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> proprietarioSenhaUseCase.updatePassword(usuarioId, senhaAntiga, null)
        );
        verifyNoInteractions(findByIdOutputPort, createOutputPort);
    }
}

