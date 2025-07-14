package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.interfaces.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.infrastructure.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsuarioDeleteAdapterTest {

    private final UsuarioRepository repository;

    private DeleteOutputPort<ClienteEntity> clienteDeleteAdapter;

    private DeleteOutputPort<ProprietarioEntity> proprietarioDeleteAdapter;

    @Autowired
    UsuarioDeleteAdapterTest(UsuarioRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        clienteDeleteAdapter = new UsuarioDeleteAdapter<>(repository);
        proprietarioDeleteAdapter = new UsuarioDeleteAdapter<>(repository);
    }

    @Test
    void deveDeletarClienteComSucesso() {
        // Arrange
        var clienteEntity = new ClienteEntity();
        clienteEntity.setNome("Charles Babbage");
        clienteEntity.setEmail("babbage@email.com");
        clienteEntity.setLogin("babbage");
        clienteEntity.setSenha("babbage!123");
        clienteEntity.setNumeroCartaoFidelidade("12345-6789-3245");

        var savedEntity = repository.save(clienteEntity);

        // Act
        clienteDeleteAdapter.delete(savedEntity);

        // Assert
        var persistedEntity = repository.findById(savedEntity.getUsuarioId());
        assertFalse(persistedEntity.isPresent(), "O cliente deve ser removido do banco");
    }

    @Test
    void deveIgnorarDelecaoDeClienteNaoPersistido() {
        // Arrange
        var clienteEntity = new ClienteEntity();
        clienteEntity.setNome("Charles Babbage");
        clienteEntity.setEmail("babbage@email.com");
        clienteEntity.setLogin("babbage");
        clienteEntity.setSenha("babbage!123");
        clienteEntity.setNumeroCartaoFidelidade("12345-6789-3245");

        // Act
        clienteDeleteAdapter.delete(clienteEntity);

        // Assert
        assertEquals(0, repository.count(), "O banco deve permanecer vazio");
    }

    @Test
    void deveDeletarProprietarioComSucesso() {
        // Arrange
        var proprietarioEntity = new ProprietarioEntity();
        proprietarioEntity.setNome("João Silva");
        proprietarioEntity.setEmail("joao@email.com");
        proprietarioEntity.setLogin("jsilva");
        proprietarioEntity.setSenha("jsilva!123");
        proprietarioEntity.setDescricao("Proprietário principal");
        
        var savedEntity = repository.save(proprietarioEntity);

        // Act
        proprietarioDeleteAdapter.delete(savedEntity);

        // Assert
        var persistedEntity = repository.findById(savedEntity.getUsuarioId());
        assertFalse(persistedEntity.isPresent(), "O proprietário deve ser removido do banco");
    }

    @Test
    void deveIgnorarDelecaoDeProprietarioNaoPersistido() {
        // Arrange
        var proprietarioEntity = new ProprietarioEntity();
        proprietarioEntity.setNome("João Silva");
        proprietarioEntity.setEmail("joao@email.com");
        proprietarioEntity.setLogin("jsilva");
        proprietarioEntity.setSenha("jsilva!123");
        proprietarioEntity.setDescricao("Proprietário principal");

        // Act
        proprietarioDeleteAdapter.delete(proprietarioEntity);

        // Assert
        assertEquals(0, repository.count(), "O banco deve permanecer vazio");
    }
}

