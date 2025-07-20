package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.interfaces.out.UsuarioSearchOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.gateways.ClienteSearchAdapter;
import br.com.fiap.tech.challenge_user.application.dtos.filters.UsuarioFiltroDto;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ClienteDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ClienteSearchAdapterTest {

    private final ClienteRepository repository;

    private UsuarioSearchOutputPort<ClienteDao> clienteSearchAdapter;

    private ClienteDao clienteDao;

    private UsuarioFiltroDto filtroDto;

    private Pageable paginacao;

    @Autowired
    ClienteSearchAdapterTest(ClienteRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        clienteSearchAdapter = new ClienteSearchAdapter(repository);

        filtroDto = new UsuarioFiltroDto(
                null, "Charles Babbage", null, null, null);

        paginacao = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "usuarioId"));

        clienteDao = new ClienteDao();
        clienteDao.setNome("Charles Babbage");
        clienteDao.setEmail("babbage@email.com");
        clienteDao.setLogin("babbage");
        clienteDao.setSenha("babbage!123");
        clienteDao.setNumeroCartaoFidelidade("12345-6789-3245");

        repository.save(clienteDao);
    }

    @Test
    void devePesquisarClienteComSucessoPeloNome() {
        // Act
        Page<ClienteDao> result = clienteSearchAdapter.search(filtroDto, paginacao);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Charles Babbage", result.getContent().getFirst().getNome());
        assertEquals("babbage@email.com", result.getContent().getFirst().getEmail());
        assertEquals("babbage", result.getContent().getFirst().getLogin());
        assertEquals("babbage!123", result.getContent().getFirst().getSenha());
        assertEquals("12345-6789-3245", result.getContent().getFirst().getNumeroCartaoFidelidade());
    }

    @Test
    void devePesquisarClientePorUsuarioId() {
        // Arrange
        var filtroDto = new UsuarioFiltroDto(
                clienteDao.getUsuarioId().toString(), null, null, null, null);

        // Act
        Page<ClienteDao> result = clienteSearchAdapter.search(filtroDto, paginacao);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(clienteDao.getUsuarioId(), result.getContent().getFirst().getUsuarioId());
        assertEquals("Charles Babbage", result.getContent().getFirst().getNome());
    }

    @Test
    void devePesquisarClientePorEmail() {
        // Arrange
        var filtroDto = new UsuarioFiltroDto(
                null, null, "babbage@email.com", null, null);

        // Act
        Page<ClienteDao> result = clienteSearchAdapter.search(filtroDto, paginacao);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("babbage@email.com", result.getContent().getFirst().getEmail());
        assertEquals("babbage", result.getContent().getFirst().getLogin());
    }

    @Test
    void devePesquisarClientePorNumeroCartaoFidelidade() {
        // Arrange
        var filtroDto = new UsuarioFiltroDto(
                null, null, null, "12345-6789-3245", null);

        // Act
        Page<ClienteDao> result = clienteSearchAdapter.search(filtroDto, paginacao);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("12345-6789-3245", result.getContent().getFirst().getNumeroCartaoFidelidade());
        assertEquals("babbage!123", result.getContent().getFirst().getSenha());
    }

    @Test
    void deveRetornarPaginaVaziaQuandoFiltroNaoCorresponde() {
        // Arrange
        filtroDto = new UsuarioFiltroDto(
                null, "Nome inexistente", null, null, null);

        // Act
        Page<ClienteDao> result = clienteSearchAdapter.search(filtroDto, paginacao);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertTrue(result.getContent().isEmpty());
    }
}

