package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.interfaces.out.UsuarioFindByLoginOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.gateways.UsuarioFindByLoginGateway;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ClienteDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsuarioFindByLoginGatewayTest {

    private final UsuarioRepository repository;

    private UsuarioFindByLoginOutputPort usuarioFindByLoginAdapter;

    @Autowired
    UsuarioFindByLoginGatewayTest(UsuarioRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        usuarioFindByLoginAdapter = new UsuarioFindByLoginGateway(repository);
    }

    @Test
    void deveEncontrarClienteExistentePorLogin() {
        // Arrange
        var clienteEntity = new ClienteDao();
        clienteEntity.setNome("Charles Babbage");
        clienteEntity.setEmail("babbage@email.com");
        clienteEntity.setLogin("babbage");
        clienteEntity.setSenha("babbage!123");
        clienteEntity.setNumeroCartaoFidelidade("12345-6789-3245");
        repository.save(clienteEntity);

        // Act
        var result = usuarioFindByLoginAdapter.findByLogin("babbage");

        // Assert
        assertTrue(result.isPresent(), "O cliente deve ser encontrado");
        var foundEntity = result.get();
        assertInstanceOf(ClienteDao.class, foundEntity, "A entidade deve ser do tipo ClienteEntity");
        assertEquals("Charles Babbage", foundEntity.getNome());
        assertEquals("babbage@email.com", foundEntity.getEmail());
        assertEquals("babbage", foundEntity.getLogin());
        assertEquals("babbage!123", foundEntity.getSenha());
        assertEquals("12345-6789-3245", ((ClienteDao) foundEntity).getNumeroCartaoFidelidade());
    }

    @Test
    void deveEncontrarProprietarioExistentePorLogin() {
        // Arrange
        var proprietarioEntity = new ProprietarioDao();
        proprietarioEntity.setNome("João Silva");
        proprietarioEntity.setEmail("joao@email.com");
        proprietarioEntity.setLogin("jsilva");
        proprietarioEntity.setSenha("jsilva!123");
        proprietarioEntity.setDescricao("Proprietário principal");
        repository.save(proprietarioEntity);

        // Act
        var result = usuarioFindByLoginAdapter.findByLogin("jsilva");

        // Assert
        assertTrue(result.isPresent(), "O proprietário deve ser encontrado");
        var foundEntity = result.get();
        assertInstanceOf(ProprietarioDao.class, foundEntity, "A entidade deve ser do tipo ProprietarioEntity");
        assertEquals("João Silva", foundEntity.getNome());
        assertEquals("joao@email.com", foundEntity.getEmail());
        assertEquals("jsilva", foundEntity.getLogin());
        assertEquals("jsilva!123", foundEntity.getSenha());
        assertEquals("Proprietário principal", ((ProprietarioDao) foundEntity).getDescricao());
    }

    @Test
    void deveRetornarVazioParaLoginInexistente() {
        // Arrange
        var nonExistentLogin = "inexistente";

        // Act
        var result = usuarioFindByLoginAdapter.findByLogin(nonExistentLogin);

        // Assert
        assertFalse(result.isPresent(), "Não deve encontrar usuário com login inexistente");
    }
}

