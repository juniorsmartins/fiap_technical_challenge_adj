package br.com.fiap.tech.challenge_user.domain.rule;

import br.com.fiap.tech.challenge_user.application.interfaces.out.UsuarioFindByLoginOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http409.UsuarioNonUniqueLoginException;
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
class UsuarioLoginStrategyTest {

    @Mock
    private UsuarioFindByLoginOutputPort findByLoginOutputPort;

    @InjectMocks
    private UsuarioLoginStrategy<Proprietario> usuarioLoginStrategy;

    private Proprietario usuario;

    private String login;

    private UUID usuarioId;

    @BeforeEach
    void setUp() {
        usuarioId = UUID.randomUUID();
        login = "joao";
        usuario = new Proprietario();
        usuario.setUsuarioId(usuarioId);
        usuario.setLogin(login);
        usuario.setEmail("joao@email.com");
        usuario.setNome("João");
    }

    @Test
    void deveNaoLancarExcecaoQuandoLoginExisteParaMesmoUsuario() {
        // Arrange
        var existente = new ProprietarioEntity();
        existente.setUsuarioId(usuarioId);
        existente.setLogin(login);

        doReturn(Optional.of(existente)).when(findByLoginOutputPort).findByLogin(login);

        // Act
        usuarioLoginStrategy.executar(usuario);

        // Assert
        verify(findByLoginOutputPort, times(1)).findByLogin(login);
        verifyNoMoreInteractions(findByLoginOutputPort);
    }

    @Test
    void deveLancarExcecaoQuandoLoginExisteParaOutroUsuario() {
        // Arrange
        var existente = new ProprietarioEntity();
        existente.setUsuarioId(UUID.randomUUID());
        existente.setLogin(login);

        doReturn(Optional.of(existente)).when(findByLoginOutputPort).findByLogin(login);

        // Act & Assert
        UsuarioNonUniqueLoginException exception = assertThrows(
                UsuarioNonUniqueLoginException.class,
                () -> usuarioLoginStrategy.executar(usuario)
        );

        assertEquals(login, exception.getValor());
        verify(findByLoginOutputPort, times(1)).findByLogin(login);
        verifyNoMoreInteractions(findByLoginOutputPort);
    }
}

