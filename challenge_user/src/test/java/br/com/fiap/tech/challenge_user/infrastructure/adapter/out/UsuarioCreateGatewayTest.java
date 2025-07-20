package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http500.UsuarioNonPersistenceException;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.gateways.UsuarioCreateGateway;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ClienteDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsuarioCreateGatewayTest {

    private final UsuarioRepository repository;

    private CreateOutputPort<ClienteDao> clienteCreateAdapter;

    private CreateOutputPort<ProprietarioDao> proprietarioCreateAdapter;

    @Autowired
    UsuarioCreateGatewayTest(UsuarioRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        clienteCreateAdapter = new UsuarioCreateGateway<>(repository);
        proprietarioCreateAdapter = new UsuarioCreateGateway<>(repository);
    }

    @Test
    void deveSalvarClienteComSucesso() {
        // Arrange
        var clienteEntity = new ClienteDao();
        clienteEntity.setNome("Charles Babbage");
        clienteEntity.setEmail("babbage@email.com");
        clienteEntity.setLogin("babbage");
        clienteEntity.setSenha("babbage!123");
        clienteEntity.setNumeroCartaoFidelidade("12345-6789-3245");

        // Act
        var savedEntity = clienteCreateAdapter.save(clienteEntity);

        // Assert
        assertNotNull(savedEntity);
        assertNotNull(savedEntity.getUsuarioId());
        assertEquals("Charles Babbage", savedEntity.getNome());
        assertEquals("babbage@email.com", savedEntity.getEmail());
        assertEquals("babbage", savedEntity.getLogin());
        assertEquals("babbage!123", savedEntity.getSenha());
        assertEquals("12345-6789-3245", savedEntity.getNumeroCartaoFidelidade());

        // Verifica persistência no banco
        var persistedEntity = repository.findById(savedEntity.getUsuarioId());
        assertTrue(persistedEntity.isPresent());
        assertEquals(savedEntity.getNome(), persistedEntity.get().getNome());
        assertEquals(savedEntity.getEmail(), persistedEntity.get().getEmail());
        assertEquals(savedEntity.getLogin(), persistedEntity.get().getLogin());
        assertEquals(savedEntity.getSenha(), persistedEntity.get().getSenha());
    }

    @Test
    void deveSalvarProprietarioComSucesso() {
        // Arrange
        var entity = new ProprietarioDao();
        entity.setNome("Charles Babbage");
        entity.setEmail("babbage@email.com");
        entity.setLogin("babbage");
        entity.setSenha("babbage!123");
        entity.setDescricao("Exemplo");

        // Act
        var savedEntity = proprietarioCreateAdapter.save(entity);

        // Assert
        assertNotNull(savedEntity);
        assertNotNull(savedEntity.getUsuarioId());
        assertEquals("Charles Babbage", savedEntity.getNome());
        assertEquals("babbage@email.com", savedEntity.getEmail());
        assertEquals("babbage", savedEntity.getLogin());
        assertEquals("babbage!123", savedEntity.getSenha());
        assertEquals("Exemplo", savedEntity.getDescricao());

        // Verifica persistência no banco
        var persistedEntity = repository.findById(savedEntity.getUsuarioId());
        assertTrue(persistedEntity.isPresent());
        assertEquals(savedEntity.getNome(), persistedEntity.get().getNome());
        assertEquals(savedEntity.getEmail(), persistedEntity.get().getEmail());
        assertEquals(savedEntity.getLogin(), persistedEntity.get().getLogin());
        assertEquals(savedEntity.getSenha(), persistedEntity.get().getSenha());
    }

    @Test
    void deveLancarExcecaoQuandoClienteComNomeNulo() {
        // Arrange
        var clienteEntity = new ClienteDao();
        clienteEntity.setEmail("babbage@email.com");
        clienteEntity.setLogin("babbage");
        clienteEntity.setSenha("babbage!123");
        clienteEntity.setNumeroCartaoFidelidade("12345-6789-3245");

        // Act & Assert
        assertThrows(UsuarioNonPersistenceException.class, () -> clienteCreateAdapter.save(clienteEntity));
    }

    @Test
    void deveLancarExcecaoQuandoProprietarioComNomeNulo() {
        // Arrange
        var entity = new ProprietarioDao();
        entity.setEmail("babbage@email.com");
        entity.setLogin("babbage");
        entity.setSenha("babbage!123");
        entity.setDescricao("Exemplo");

        // Act & Assert
        assertThrows(UsuarioNonPersistenceException.class, () -> proprietarioCreateAdapter.save(entity));
    }

    @Test
    void deveLancarExcecaoQuandoClienteComEmailNulo() {
        // Arrange
        var clienteEntity = new ClienteDao();
        clienteEntity.setNome("Charles Babbage");
        clienteEntity.setLogin("babbage");
        clienteEntity.setSenha("babbage!123");
        clienteEntity.setNumeroCartaoFidelidade("12345-6789-3245");

        // Act & Assert
        assertThrows(UsuarioNonPersistenceException.class, () -> clienteCreateAdapter.save(clienteEntity));
    }

    @Test
    void deveLancarExcecaoQuandoProprietarioComEmailNulo() {
        // Arrange
        var entity = new ProprietarioDao();
        entity.setNome("Charles Babbage");
        entity.setLogin("babbage");
        entity.setSenha("babbage!123");
        entity.setDescricao("Exemplo");

        // Act & Assert
        assertThrows(UsuarioNonPersistenceException.class, () -> proprietarioCreateAdapter.save(entity));
    }

    @Test
    void deveLancarExcecaoQuandoClienteComLoginNulo() {
        // Arrange
        var clienteEntity = new ClienteDao();
        clienteEntity.setNome("Charles Babbage");
        clienteEntity.setEmail("babbage@email.com");
        clienteEntity.setSenha("babbage!123");
        clienteEntity.setNumeroCartaoFidelidade("12345-6789-3245");

        // Act & Assert
        assertThrows(UsuarioNonPersistenceException.class, () -> clienteCreateAdapter.save(clienteEntity));
    }

    @Test
    void deveLancarExcecaoQuandoProprietarioComLoginNulo() {
        // Arrange
        var entity = new ProprietarioDao();
        entity.setNome("Charles Babbage");
        entity.setEmail("babbage@email.com");
        entity.setSenha("babbage!123");
        entity.setDescricao("Exemplo");

        // Act & Assert
        assertThrows(UsuarioNonPersistenceException.class, () -> proprietarioCreateAdapter.save(entity));
    }

    @Test
    void deveLancarExcecaoQuandoClienteComSenhaNulo() {
        // Arrange
        var clienteEntity = new ClienteDao();
        clienteEntity.setNome("Charles Babbage");
        clienteEntity.setEmail("babbage@email.com");
        clienteEntity.setLogin("babbage");
        clienteEntity.setNumeroCartaoFidelidade("12345-6789-3245");

        // Act & Assert
        assertThrows(UsuarioNonPersistenceException.class, () -> clienteCreateAdapter.save(clienteEntity));
    }

    @Test
    void deveLancarExcecaoQuandoProprietarioComSenhaNulo() {
        // Arrange
        var entity = new ProprietarioDao();
        entity.setNome("Charles Babbage");
        entity.setEmail("babbage@email.com");
        entity.setLogin("babbage");
        entity.setDescricao("Exemplo");

        // Act & Assert
        assertThrows(UsuarioNonPersistenceException.class, () -> proprietarioCreateAdapter.save(entity));
    }

    @Test
    void deveGerarUsuarioIdAutomaticamente() {
        // Arrange
        var clienteEntity = new ClienteDao();
        clienteEntity.setNome("Charles Babbage");
        clienteEntity.setEmail("babbage@email.com");
        clienteEntity.setLogin("babbage");
        clienteEntity.setSenha("babbage!123");
        clienteEntity.setNumeroCartaoFidelidade("12345-6789-3245");

        // Act
        var savedEntity = clienteCreateAdapter.save(clienteEntity);

        // Assert
        assertNotNull(savedEntity.getUsuarioId(), "O usuarioId deve ser gerado automaticamente");
        assertTrue(repository.existsById(savedEntity.getUsuarioId()), "A entidade deve estar persistida no banco");
    }
}

