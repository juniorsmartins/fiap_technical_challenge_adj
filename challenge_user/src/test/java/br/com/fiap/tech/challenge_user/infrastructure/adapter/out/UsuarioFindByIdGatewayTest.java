package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.gateways.UsuarioFindByIdGateway;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ClienteDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsuarioFindByIdGatewayTest {

    private final UsuarioRepository repository;

    private FindByIdOutputPort<ClienteDao> clienteFindByIdAdapter;

    private FindByIdOutputPort<ProprietarioDao> proprietarioFindByIdAdapter;

    @Autowired
    UsuarioFindByIdGatewayTest(UsuarioRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        clienteFindByIdAdapter = new UsuarioFindByIdGateway<>(repository);
        proprietarioFindByIdAdapter = new UsuarioFindByIdGateway<>(repository);
    }

    @Test
    void deveEncontrarClienteExistentePorId() {
        // Arrange
        var clienteEntity = new ClienteDao();
        clienteEntity.setNome("Charles Babbage");
        clienteEntity.setEmail("babbage@email.com");
        clienteEntity.setLogin("babbage");
        clienteEntity.setSenha("babbage!123");
        clienteEntity.setNumeroCartaoFidelidade("12345-6789-3245");
        var savedEntity = repository.save(clienteEntity);

        // Act
        var result = clienteFindByIdAdapter.findById(savedEntity.getUsuarioId());

        // Assert
        assertTrue(result.isPresent(), "O cliente deve ser encontrado");
        var foundEntity = result.get();
        assertInstanceOf(ClienteDao.class, foundEntity, "A entidade deve ser do tipo ClienteEntity");
        assertEquals(savedEntity.getUsuarioId(), foundEntity.getUsuarioId());
        assertEquals("Charles Babbage", foundEntity.getNome());
        assertEquals("babbage@email.com", foundEntity.getEmail());
        assertEquals("babbage", foundEntity.getLogin());
        assertEquals("babbage!123", foundEntity.getSenha());
        assertEquals("12345-6789-3245", foundEntity.getNumeroCartaoFidelidade());
    }

    @Test
    void deveEncontrarProprietarioExistentePorId() {
        // Arrange
        var proprietarioEntity = new ProprietarioDao();
        proprietarioEntity.setNome("João Silva");
        proprietarioEntity.setEmail("joao@email.com");
        proprietarioEntity.setLogin("jsilva");
        proprietarioEntity.setSenha("jsilva!123");
        proprietarioEntity.setDescricao("Proprietário principal");
        var savedEntity = repository.save(proprietarioEntity);

        // Act
        var result = proprietarioFindByIdAdapter.findById(savedEntity.getUsuarioId());

        // Assert
        assertTrue(result.isPresent(), "O proprietário deve ser encontrado");
        var foundEntity = result.get();
        assertInstanceOf(ProprietarioDao.class, foundEntity, "A entidade deve ser do tipo ProprietarioEntity");
        assertEquals(savedEntity.getUsuarioId(), foundEntity.getUsuarioId());
        assertEquals("João Silva", foundEntity.getNome());
        assertEquals("joao@email.com", foundEntity.getEmail());
        assertEquals("jsilva", foundEntity.getLogin());
        assertEquals("jsilva!123", foundEntity.getSenha());
        assertEquals("Proprietário principal", foundEntity.getDescricao());
    }

    @Test
    void deveRetornarVazioParaIdInexistente() {
        // Arrange
        var nonExistentId = UUID.randomUUID();

        // Act
        var result = clienteFindByIdAdapter.findById(nonExistentId);

        // Assert
        assertFalse(result.isPresent(), "Não deve encontrar usuário com ID inexistente");
    }
}

