package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.DaoPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.RecursoNotFoundException;
import br.com.fiap.tech.challenge_user.domain.entities.Cliente;
import br.com.fiap.tech.challenge_user.domain.entities.Endereco;
import br.com.fiap.tech.challenge_user.domain.rules.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.domain.rules.update.EnderecoUpdateRule;
import br.com.fiap.tech.challenge_user.domain.rules.update.UsuarioUpdateRule;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ClienteDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.EnderecoDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteUpdateUseCaseTest {

    @Mock
    private DaoPresenter<Cliente, ClienteDao> daoPresenter;

    @Mock
    private CreateOutputPort<ClienteDao> createOutputPort;

    @Mock
    private FindByIdOutputPort<ClienteDao> findByIdOutputPort;

    @Mock
    private UsuarioUpdateRule<Cliente, ClienteDao> usuarioUpdateRule;

    @Mock
    private EnderecoUpdateRule<Cliente, ClienteDao> enderecoUpdateRule;

    @Mock
    private UsuarioRulesStrategy<Cliente> rule1;

    @Mock
    private UsuarioRulesStrategy<Cliente> rule2;

    private ClienteUpdateUseCase clienteUpdateUseCase;

    private Cliente cliente;

    private ClienteDao clienteDao;

    private UUID clienteId;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();

        var endereco = new Endereco();
        endereco.setCep("12345-678");
        endereco.setLogradouro("Rua Exemplo");
        endereco.setNumero("123");

        cliente = new Cliente(
                "Maria", "maria@email.com", "maria", "maria123",
                endereco, "123456789"
        );

        clienteDao = new ClienteDao();
        clienteDao.setUsuarioId(clienteId);
        clienteDao.setNome("Maria");
        clienteDao.setEmail("maria@email.com");
        clienteDao.setLogin("maria");
        clienteDao.setSenha("maria123");
        clienteDao.setNumeroCartaoFidelidade("123456789");
        clienteDao.setEndereco(new EnderecoDao(UUID.randomUUID(), "12345-678", "Rua Exemplo", "123"));

        List<UsuarioRulesStrategy<Cliente>> rulesStrategy = List.of(rule1, rule2);
        clienteUpdateUseCase = new ClienteUpdateUseCase(
                daoPresenter, createOutputPort, findByIdOutputPort, usuarioUpdateRule, enderecoUpdateRule, rulesStrategy
        );
    }

    @Test
    void deveAtualizarClienteQuandoDadosSaoValidos() {
        // Arrange
        when(findByIdOutputPort.findById(clienteId)).thenReturn(Optional.of(clienteDao));
        when(usuarioUpdateRule.updateUser(cliente, clienteDao)).thenReturn(clienteDao);
        when(enderecoUpdateRule.updateAddress(cliente, clienteDao)).thenReturn(clienteDao);
        when(createOutputPort.save(clienteDao)).thenReturn(clienteDao);
        when(daoPresenter.toDomain(clienteDao)).thenReturn(cliente);

        // Act
        Cliente result = clienteUpdateUseCase.update(clienteId, cliente);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUsuarioId()).isEqualTo(clienteId); // Verifica que o ID foi definido
        assertThat(result.getNome()).isEqualTo(cliente.getNome());
        assertThat(result.getEmail()).isEqualTo(cliente.getEmail());
        assertThat(result.getLogin()).isEqualTo(cliente.getLogin());
        assertThat(result.getSenha()).isEqualTo(cliente.getSenha());
        assertThat(result.getNumeroCartaoFidelidade()).isEqualTo(cliente.getNumeroCartaoFidelidade());
        assertThat(result.getEndereco()).isEqualTo(cliente.getEndereco());
        verify(rule1, times(1)).executar(cliente);
        verify(rule2, times(1)).executar(cliente);
        verify(findByIdOutputPort, times(1)).findById(clienteId);
        verify(usuarioUpdateRule, times(1)).updateUser(cliente, clienteDao);
        verify(enderecoUpdateRule, times(1)).updateAddress(cliente, clienteDao);
        verify(createOutputPort, times(1)).save(clienteDao);
        verify(daoPresenter, times(1)).toDomain(clienteDao);
        verifyNoMoreInteractions(rule1, rule2, findByIdOutputPort, usuarioUpdateRule, enderecoUpdateRule, createOutputPort, daoPresenter);
    }

    @Test
    void deveLancarRecursoNotFoundExceptionQuandoClienteNaoEhEncontrado() {
        // Arrange
        when(findByIdOutputPort.findById(clienteId)).thenReturn(Optional.empty());

        // Act & Assert
        RecursoNotFoundException exception = assertThrows(
                RecursoNotFoundException.class,
                () -> clienteUpdateUseCase.update(clienteId, cliente)
        );

        assertThat(exception.getId()).isEqualTo(clienteId);
        verify(findByIdOutputPort, times(1)).findById(clienteId);
        verifyNoInteractions(rule1, rule2, usuarioUpdateRule, enderecoUpdateRule, createOutputPort, daoPresenter);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoIdEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> clienteUpdateUseCase.update(null, cliente)
        );

        verifyNoInteractions(rule1, rule2, findByIdOutputPort, usuarioUpdateRule, enderecoUpdateRule, createOutputPort, daoPresenter);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoUsuarioEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> clienteUpdateUseCase.update(clienteId, null)
        );

        verifyNoInteractions(rule1, rule2, findByIdOutputPort, usuarioUpdateRule, enderecoUpdateRule, createOutputPort, daoPresenter);
    }
}

