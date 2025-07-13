package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.presenters.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.RecursoNotFoundException;
import br.com.fiap.tech.challenge_user.domain.model.Cliente;
import br.com.fiap.tech.challenge_user.domain.model.Endereco;
import br.com.fiap.tech.challenge_user.domain.rule.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.domain.rule.update.EnderecoUpdateRule;
import br.com.fiap.tech.challenge_user.domain.rule.update.UsuarioUpdateRule;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.infrastructure.entity.EnderecoEntity;
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

        var endereco = new Endereco();
        endereco.setCep("12345-678");
        endereco.setLogradouro("Rua Exemplo");
        endereco.setNumero("123");

        cliente = new Cliente(
                "Maria", "maria@email.com", "maria", "maria123",
                endereco, "123456789"
        );

        clienteEntity = new ClienteEntity();
        clienteEntity.setUsuarioId(clienteId);
        clienteEntity.setNome("Maria");
        clienteEntity.setEmail("maria@email.com");
        clienteEntity.setLogin("maria");
        clienteEntity.setSenha("maria123");
        clienteEntity.setNumeroCartaoFidelidade("123456789");
        clienteEntity.setEndereco(new EnderecoEntity(UUID.randomUUID(), "12345-678", "Rua Exemplo", "123"));

        List<UsuarioRulesStrategy<Cliente>> rulesStrategy = List.of(rule1, rule2);
        clienteUpdateUseCase = new ClienteUpdateUseCase(
                entityMapper, createOutputPort, findByIdOutputPort, usuarioUpdateRule, enderecoUpdateRule, rulesStrategy
        );
    }

    @Test
    void deveAtualizarClienteQuandoDadosSaoValidos() {
        // Arrange
        when(findByIdOutputPort.findById(clienteId)).thenReturn(Optional.of(clienteEntity));
        when(usuarioUpdateRule.updateUser(cliente, clienteEntity)).thenReturn(clienteEntity);
        when(enderecoUpdateRule.updateAddress(cliente, clienteEntity)).thenReturn(clienteEntity);
        when(createOutputPort.save(clienteEntity)).thenReturn(clienteEntity);
        when(entityMapper.toDomain(clienteEntity)).thenReturn(cliente);

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
        verify(usuarioUpdateRule, times(1)).updateUser(cliente, clienteEntity);
        verify(enderecoUpdateRule, times(1)).updateAddress(cliente, clienteEntity);
        verify(createOutputPort, times(1)).save(clienteEntity);
        verify(entityMapper, times(1)).toDomain(clienteEntity);
        verifyNoMoreInteractions(rule1, rule2, findByIdOutputPort, usuarioUpdateRule, enderecoUpdateRule, createOutputPort, entityMapper);
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
        verifyNoInteractions(rule1, rule2, usuarioUpdateRule, enderecoUpdateRule, createOutputPort, entityMapper);
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

