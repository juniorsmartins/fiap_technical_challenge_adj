package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.infrastructure.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsuarioCreateAdapterTest {

    private final UsuarioRepository repository;

    private CreateOutputPort<ClienteEntity> usuarioCreateAdapter;

    @Autowired
    UsuarioCreateAdapterTest(UsuarioRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        usuarioCreateAdapter = new UsuarioCreateAdapter<>(repository);
    }

    @Test
    void deveSalvarClienteComSucesso() {
        // Arrange
        var clienteEntity = new ClienteEntity();
        clienteEntity.setNome("Charles Babbage");
        clienteEntity.setEmail("babbage@email.com");
        clienteEntity.setLogin("babbage");
        clienteEntity.setSenha("babbage!123");
        clienteEntity.setNumeroCartaoFidelidade("12345-6789-3245");

        // Act
        var savedEntity = usuarioCreateAdapter.save(clienteEntity);

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
    }

    @Test
    void deveLancarExcecaoQuandoNomeNulo() {
        // Arrange
        var clienteEntity = new ClienteEntity();
        clienteEntity.setEmail("babbage@email.com");
        clienteEntity.setLogin("babbage");
        clienteEntity.setSenha("babbage!123");
        clienteEntity.setNumeroCartaoFidelidade("12345-6789-3245");

        // Act & Assert
        assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioCreateAdapter.save(clienteEntity);
        });
    }

    @Test
    void deveLancarExcecaoQuandoEmailNulo() {
        // Arrange
        var clienteEntity = new ClienteEntity();
        clienteEntity.setNome("Charles Babbage");
        clienteEntity.setLogin("babbage");
        clienteEntity.setSenha("babbage!123");
        clienteEntity.setNumeroCartaoFidelidade("12345-6789-3245");

        // Act & Assert
        assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioCreateAdapter.save(clienteEntity);
        });
    }

    @Test
    void deveLancarExcecaoQuandoLoginNulo() {
        // Arrange
        var clienteEntity = new ClienteEntity();
        clienteEntity.setNome("Charles Babbage");
        clienteEntity.setEmail("babbage@email.com");
        clienteEntity.setSenha("babbage!123");
        clienteEntity.setNumeroCartaoFidelidade("12345-6789-3245");

        // Act & Assert
        assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioCreateAdapter.save(clienteEntity);
        });
    }

    @Test
    void deveLancarExcecaoQuandoSenhaNulo() {
        // Arrange
        var clienteEntity = new ClienteEntity();
        clienteEntity.setNome("Charles Babbage");
        clienteEntity.setEmail("babbage@email.com");
        clienteEntity.setLogin("babbage");
        clienteEntity.setNumeroCartaoFidelidade("12345-6789-3245");

        // Act & Assert
        assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioCreateAdapter.save(clienteEntity);
        });
    }

    @Test
    void deveGerarUsuarioIdAutomaticamente() {
        // Arrange
        var clienteEntity = new ClienteEntity();
        clienteEntity.setNome("Charles Babbage");
        clienteEntity.setEmail("babbage@email.com");
        clienteEntity.setLogin("babbage");
        clienteEntity.setSenha("babbage!123");
        clienteEntity.setNumeroCartaoFidelidade("12345-6789-3245");

        // Act
        var savedEntity = usuarioCreateAdapter.save(clienteEntity);

        // Assert
        assertNotNull(savedEntity.getUsuarioId(), "O usuarioId deve ser gerado automaticamente");
        assertTrue(repository.existsById(savedEntity.getUsuarioId()), "A entidade deve estar persistida no banco");
    }
}

