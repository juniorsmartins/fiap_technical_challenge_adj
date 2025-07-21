package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.DaoPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http409.UsuarioNonUniqueNomeException;
import br.com.fiap.tech.challenge_user.domain.entities.Cliente;
import br.com.fiap.tech.challenge_user.domain.rules.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ClienteDao;
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
    private DaoPresenter<Cliente, ClienteDao> daoPresenter;

    @Mock
    private CreateOutputPort<ClienteDao> createOutputPort;

    @Mock
    private UsuarioRulesStrategy<Cliente> rule1;

    @Mock
    private UsuarioRulesStrategy<Cliente> rule2;

    @InjectMocks
    private ClienteCreateUseCase clienteCreateUseCase;

    private Cliente cliente;

    private ClienteDao clienteDao;

    @BeforeEach
    void setUp() {
        UUID usuarioId = UUID.randomUUID();
        cliente = new Cliente();
        cliente.setUsuarioId(usuarioId);
        cliente.setNome("Maria");
        cliente.setEmail("maria@email.com");
        cliente.setLogin("maria");

        clienteDao = new ClienteDao();
        clienteDao.setUsuarioId(usuarioId);
        clienteDao.setNome("Maria");
        clienteDao.setEmail("maria@email.com");
        clienteDao.setLogin("maria");

        List<UsuarioRulesStrategy<Cliente>> rulesStrategy = List.of(rule1, rule2);
        clienteCreateUseCase = new ClienteCreateUseCase(daoPresenter, createOutputPort, rulesStrategy);
    }

    @Test
    void deveCriarClienteQuandoRegrasSaoValidas() {
        // Arrange
        when(daoPresenter.toEntity(cliente)).thenReturn(clienteDao);
        when(createOutputPort.save(clienteDao)).thenReturn(clienteDao);
        when(daoPresenter.toDomain(clienteDao)).thenReturn(cliente);

        // Act
        Cliente result = clienteCreateUseCase.create(cliente);

        // Assert
        assertNotNull(result);
        assertEquals(cliente, result);
        verify(rule1, times(1)).executar(cliente);
        verify(rule2, times(1)).executar(cliente);
        verify(daoPresenter, times(1)).toEntity(cliente);
        verify(createOutputPort, times(1)).save(clienteDao);
        verify(daoPresenter, times(1)).toDomain(clienteDao);
        verifyNoMoreInteractions(rule1, rule2, daoPresenter, createOutputPort);
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
        verifyNoInteractions(rule2, daoPresenter, createOutputPort);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoUsuarioEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> clienteCreateUseCase.create(null)
        );
        verifyNoInteractions(rule1, rule2, daoPresenter, createOutputPort);
    }
}

