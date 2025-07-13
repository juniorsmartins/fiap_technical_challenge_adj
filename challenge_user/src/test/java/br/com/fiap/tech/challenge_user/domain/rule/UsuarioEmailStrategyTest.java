package br.com.fiap.tech.challenge_user.domain.rule;

import br.com.fiap.tech.challenge_user.application.port.out.UsuarioFindByEmailOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http409.UsuarioNonUniqueEmailException;
import br.com.fiap.tech.challenge_user.domain.model.Proprietario;
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
class UsuarioEmailStrategyTest {

    @Mock
    private UsuarioFindByEmailOutputPort findByEmailOutputPort;

    @InjectMocks
    private UsuarioEmailStrategy<Proprietario> usuarioEmailStrategy;

    private Proprietario usuario;
    private String email;
    private UUID usuarioId;

    @BeforeEach
    void setUp() {
        usuarioId = UUID.randomUUID();
        email = "joao@email.com";
        usuario = new Proprietario();
        usuario.setUsuarioId(usuarioId);
        usuario.setEmail(email);
        usuario.setNome("João");
        usuario.setLogin("joao");
    }

    @Test
    void deveNaoLancarExcecaoQuandoEmailNaoExiste() {
        // Arrange
        when(findByEmailOutputPort.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        usuarioEmailStrategy.executar(usuario);

        // Assert
        verify(findByEmailOutputPort).findByEmail(email);
        verifyNoMoreInteractions(findByEmailOutputPort);
    }

    @Test
    void deveLancarExcecaoQuandoEmailExisteParaOutroUsuario() {
        // Arrange
        var existente = new ProprietarioEntity();
        existente.setUsuarioId(UUID.randomUUID());
        existente.setEmail(email);
        when(findByEmailOutputPort.findByEmail(email)).thenReturn(Optional.of(existente));

        // Act & Assert
        UsuarioNonUniqueEmailException exception = assertThrows(
                UsuarioNonUniqueEmailException.class,
                () -> usuarioEmailStrategy.executar(usuario)
        );
        assertEquals(email, exception.getValor());
        verify(findByEmailOutputPort).findByEmail(email);
        verifyNoMoreInteractions(findByEmailOutputPort);
    }

    @Test
    void deveNaoLancarExcecaoQuandoEmailExisteParaMesmoUsuario() {
        // Arrange
        var existente = new ProprietarioEntity();
        existente.setUsuarioId(usuarioId);
        existente.setEmail(email);
        when(findByEmailOutputPort.findByEmail(email)).thenReturn(Optional.of(existente));

        // Act
        usuarioEmailStrategy.executar(usuario);

        // Assert
        verify(findByEmailOutputPort).findByEmail(email);
        verifyNoMoreInteractions(findByEmailOutputPort);
    }
}

