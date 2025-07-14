package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.EntityMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http409.UsuarioNonUniqueNomeException;
import br.com.fiap.tech.challenge_user.domain.models.Cliente;
import br.com.fiap.tech.challenge_user.domain.rules.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ClienteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteCreateUseCaseTest {

    @Mock
    private EntityMapper<Cliente, ClienteEntity> entityMapper;

    @Mock
    private CreateOutputPort<ClienteEntity> createOutputPort;

    @Mock
    private UsuarioRulesStrategy<Cliente> rule1;

    @Mock
    private UsuarioRulesStrategy<Cliente> rule2;

    @InjectMocks
    private ClienteCreateUseCase clienteCreateUseCase;

    private Cliente cliente;

    private ClienteEntity clienteEntity;

    @BeforeEach
    void setUp() {
        UUID usuarioId = UUID.randomUUID();
        cliente = new Cliente();
        cliente.setUsuarioId(usuarioId);
        cliente.setNome("Maria");
        cliente.setEmail("maria@email.com");
        cliente.setLogin("maria");

        clienteEntity = new ClienteEntity();
        clienteEntity.setUsuarioId(usuarioId);
        clienteEntity.setNome("Maria");
        clienteEntity.setEmail("maria@email.com");
        clienteEntity.setLogin("maria");

        List<UsuarioRulesStrategy<Cliente>> rulesStrategy = List.of(rule1, rule2);
        clienteCreateUseCase = new ClienteCreateUseCase(entityMapper, createOutputPort, rulesStrategy);
    }

    @Test
    void deveCriarClienteQuandoRegrasSaoValidas() {
        // Arrange
        when(entityMapper.toEntity(cliente)).thenReturn(clienteEntity);
        when(createOutputPort.save(clienteEntity)).thenReturn(clienteEntity);
        when(entityMapper.toDomain(clienteEntity)).thenReturn(cliente);

        // Act
        Cliente result = clienteCreateUseCase.create(cliente);

        // Assert
        assertNotNull(result);
        assertEquals(cliente, result);
        verify(rule1, times(1)).executar(cliente);
        verify(rule2, times(1)).executar(cliente);
        verify(entityMapper, times(1)).toEntity(cliente);
        verify(createOutputPort, times(1)).save(clienteEntity);
        verify(entityMapper, times(1)).toDomain(clienteEntity);
        verifyNoMoreInteractions(rule1, rule2, entityMapper, createOutputPort);
    }

    @Test
    void deveLancarExcecaoQuandoRegraFalha() {
        // Arrange
        doThrow(new UsuarioNonUniqueNomeException("Maria")).when(rule1).executar(cliente);

        // Act & Assert
        UsuarioNonUniqueNomeException exception = assertThrows(
                UsuarioNonUniqueNomeException.class,
                () -> clienteCreateUseCase.create(cliente)
        );
        assertEquals("Maria", exception.getValor());
        verify(rule1, times(1)).executar(cliente);
        verifyNoInteractions(rule2, entityMapper, createOutputPort);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoUsuarioEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> clienteCreateUseCase.create(null)
        );
        verifyNoInteractions(rule1, rule2, entityMapper, createOutputPort);
    }
}

