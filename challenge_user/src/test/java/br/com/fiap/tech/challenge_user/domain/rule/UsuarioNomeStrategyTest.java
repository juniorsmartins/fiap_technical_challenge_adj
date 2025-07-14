package br.com.fiap.tech.challenge_user.domain.rule;

import br.com.fiap.tech.challenge_user.application.interfaces.out.UsuarioFindByNomeOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http409.UsuarioNonUniqueNomeException;
import br.com.fiap.tech.challenge_user.domain.model.Proprietario;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ProprietarioEntity;
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
class UsuarioNomeStrategyTest {

    @Mock
    private UsuarioFindByNomeOutputPort findByNomeOutputPort;

    @InjectMocks
    private UsuarioNomeStrategy<Proprietario> usuarioNomeStrategy;

    private Proprietario usuario;
    private String nome;
    private UUID usuarioId;

    @BeforeEach
    void setUp() {
        usuarioId = UUID.randomUUID();
        nome = "João";
        usuario = new Proprietario();
        usuario.setUsuarioId(usuarioId);
        usuario.setNome(nome);
        usuario.setEmail("joao@email.com");
        usuario.setLogin("joao");
    }

    @Test
    void deveNaoLancarExcecaoQuandoNomeExisteParaMesmoUsuario() {
        // Arrange
        var existente = new ProprietarioEntity();
        existente.setUsuarioId(usuarioId);
        existente.setNome(nome);

        doReturn(Optional.of(existente)).when(findByNomeOutputPort).findByNome(nome);

        // Act
        usuarioNomeStrategy.executar(usuario);

        // Assert
        verify(findByNomeOutputPort, times(1)).findByNome(nome);
        verifyNoMoreInteractions(findByNomeOutputPort);
    }

    @Test
    void deveLancarExcecaoQuandoNomeExisteParaOutroUsuario() {
        // Arrange
        var existente = new ProprietarioEntity();
        existente.setUsuarioId(UUID.randomUUID());
        existente.setNome(nome);

        doReturn(Optional.of(existente)).when(findByNomeOutputPort).findByNome(nome);

        // Act & Assert
        UsuarioNonUniqueNomeException exception = assertThrows(
                UsuarioNonUniqueNomeException.class,
                () -> usuarioNomeStrategy.executar(usuario)
        );

        assertEquals(nome, exception.getValor());
        verify(findByNomeOutputPort, times(1)).findByNome(nome);
        verifyNoMoreInteractions(findByNomeOutputPort);
    }
}