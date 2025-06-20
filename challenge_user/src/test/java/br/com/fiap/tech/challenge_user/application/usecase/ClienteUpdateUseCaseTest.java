package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.exception.http404.UsuarioNotFoundException;
import br.com.fiap.tech.challenge_user.domain.exception.http409.UsuarioNonUniqueNomeException;
import br.com.fiap.tech.challenge_user.domain.model.Cliente;
import br.com.fiap.tech.challenge_user.domain.rule.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.domain.rule.update.EnderecoUpdateRule;
import br.com.fiap.tech.challenge_user.domain.rule.update.UsuarioUpdateRule;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteUpdateUseCaseTest {

    @Mock
    private EntityMapper<Cliente, ClienteEntity> entityMapper;

    @Mock
    private CreateOutputPort<ClienteEntity> createOutputPort;

    @Mock
    private FindByIdOutputPort<ClienteEntity> findByIdOutputPort;

    @Mock
    private UsuarioUpdateRule<Cliente, ClienteEntity> usuarioUpdateRule;

    @Mock
    private EnderecoUpdateRule<Cliente, ClienteEntity> enderecoUpdateRule;

    @Mock
    private UsuarioRulesStrategy<Cliente> rule1;

    @Mock
    private UsuarioRulesStrategy<Cliente> rule2;

    private ClienteUpdateUseCase clienteUpdateUseCase;

    private Cliente cliente;

    private ClienteEntity clienteEntity;

    private UUID clienteId;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();
        cliente = new Cliente();
        cliente.setUsuarioId(clienteId);
        cliente.setNome("Maria");
        cliente.setEmail("maria@email.com");
        cliente.setLogin("maria");

        clienteEntity = new ClienteEntity();
        clienteEntity.setUsuarioId(clienteId);
        clienteEntity.setNome("Maria");
        clienteEntity.setEmail("maria@email.com");
        clienteEntity.setLogin("maria");

        List<UsuarioRulesStrategy<Cliente>> rulesStrategy = List.of(rule1, rule2);
        clienteUpdateUseCase = new ClienteUpdateUseCase(
                entityMapper,
                createOutputPort,
                findByIdOutputPort,
                usuarioUpdateRule,
                enderecoUpdateRule,
                rulesStrategy
        );
    }

    @Test
    void deveAtualizarClienteQuandoDadosSaoValidos() {
        // Arrange
        doReturn(Optional.of(clienteEntity)).when(findByIdOutputPort).findById(clienteId);
        doReturn(clienteEntity).when(usuarioUpdateRule).updateUser(cliente, clienteEntity);
        doReturn(clienteEntity).when(enderecoUpdateRule).updateAddress(cliente, clienteEntity);
        doReturn(clienteEntity).when(createOutputPort).save(clienteEntity);
        doReturn(cliente).when(entityMapper).toDomain(clienteEntity);

        // Act
        Cliente result = clienteUpdateUseCase.update(clienteId, cliente);

        // Assert
        assertNotNull(result);
        assertEquals(cliente, result);
        verify(rule1, times(1)).executar(cliente);
        verify(rule2, times(1)).executar(cliente);
        verify(findByIdOutputPort, times(1)).findById(clienteId);
        verify(usuarioUpdateRule, times(1)).updateUser(cliente, clienteEntity);
        verify(enderecoUpdateRule, times(1)).updateAddress(cliente, clienteEntity);
        verify(createOutputPort, times(1)).save(clienteEntity);
        verify(entityMapper, times(1)).toDomain(clienteEntity);
        verifyNoMoreInteractions(rule1, rule2, findByIdOutputPort, usuarioUpdateRule, enderecoUpdateRule, createOutputPort, entityMapper);
    }

    @Test
    void deveLancarUsuarioNotFoundExceptionQuandoClienteNaoEhEncontrado() {
        // Arrange
        doReturn(Optional.empty()).when(findByIdOutputPort).findById(clienteId);

        // Act & Assert
        UsuarioNotFoundException exception = assertThrows(
                UsuarioNotFoundException.class,
                () -> clienteUpdateUseCase.update(clienteId, cliente)
        );
        assertEquals(clienteId, exception.getId());
        verify(rule1, times(1)).executar(cliente);
        verify(rule2, times(1)).executar(cliente);
        verify(findByIdOutputPort, times(1)).findById(clienteId);
        verifyNoInteractions(usuarioUpdateRule, enderecoUpdateRule, createOutputPort, entityMapper);
        verifyNoMoreInteractions(rule1, rule2, findByIdOutputPort);
    }

    @Test
    void deveLancarExcecaoQuandoRegraFalha() {
        // Arrange
        doThrow(new UsuarioNonUniqueNomeException("Maria")).when(rule1).executar(cliente);

        // Act & Assert
        UsuarioNonUniqueNomeException exception = assertThrows(
                UsuarioNonUniqueNomeException.class,
                () -> clienteUpdateUseCase.update(clienteId, cliente)
        );
        assertEquals("Maria", exception.getValor());
        verify(rule1, times(1)).executar(cliente);
        verifyNoInteractions(rule2, findByIdOutputPort, usuarioUpdateRule, enderecoUpdateRule, createOutputPort, entityMapper);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoIdEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> clienteUpdateUseCase.update(null, cliente)
        );
        verifyNoInteractions(rule1, rule2, findByIdOutputPort, usuarioUpdateRule, enderecoUpdateRule, createOutputPort, entityMapper);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoUsuarioEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> clienteUpdateUseCase.update(clienteId, null)
        );
        verifyNoInteractions(rule1, rule2, findByIdOutputPort, usuarioUpdateRule, enderecoUpdateRule, createOutputPort, entityMapper);
    }
}

