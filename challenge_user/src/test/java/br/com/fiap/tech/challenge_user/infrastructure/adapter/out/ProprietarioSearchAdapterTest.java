package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.interfaces.out.UsuarioSearchOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.gateways.ProprietarioSearchAdapter;
import br.com.fiap.tech.challenge_user.application.dtos.filters.UsuarioFiltroDto;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.ProprietarioRepository;
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
class ProprietarioSearchAdapterTest {

    private final ProprietarioRepository repository;

    private UsuarioSearchOutputPort<ProprietarioEntity> proprietarioSearchAdapter;

    private ProprietarioEntity proprietarioEntity;

    private UsuarioFiltroDto filtroDto;

    private Pageable paginacao;

    @Autowired
    ProprietarioSearchAdapterTest(ProprietarioRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        proprietarioSearchAdapter = new ProprietarioSearchAdapter(repository);

        filtroDto = new UsuarioFiltroDto(
                null, "Alan Turing", null, null, null);

        paginacao = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "usuarioId"));

        proprietarioEntity = new ProprietarioEntity();
        proprietarioEntity.setNome("Alan Turing");
        proprietarioEntity.setEmail("turing@email.com");
        proprietarioEntity.setLogin("aturing");
        proprietarioEntity.setSenha("aturing!123");
        proprietarioEntity.setDescricao("Proprietário principal");

        repository.save(proprietarioEntity);
    }

    @Test
    void devePesquisarProprietarioComSucessoPeloNome() {
        // Act
        Page<ProprietarioEntity> result = proprietarioSearchAdapter.search(filtroDto, paginacao);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Alan Turing", result.getContent().getFirst().getNome());
        assertEquals("turing@email.com", result.getContent().getFirst().getEmail());
        assertEquals("aturing", result.getContent().getFirst().getLogin());
        assertEquals("aturing!123", result.getContent().getFirst().getSenha());
        assertEquals("Proprietário principal", result.getContent().getFirst().getDescricao());
    }

    @Test
    void devePesquisarProprietarioPorUsuarioId() {
        // Arrange
        var filtroDto = new UsuarioFiltroDto(
                proprietarioEntity.getUsuarioId().toString(), null, null, null, null);

        // Act
        Page<ProprietarioEntity> result = proprietarioSearchAdapter.search(filtroDto, paginacao);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(proprietarioEntity.getUsuarioId(), result.getContent().getFirst().getUsuarioId());
        assertEquals("Alan Turing", result.getContent().getFirst().getNome());
    }

    @Test
    void devePesquisarProprietarioPorEmail() {
        // Arrange
        var filtroDto = new UsuarioFiltroDto(
                null, null, "turing@email.com", null, null);

        // Act
        Page<ProprietarioEntity> result = proprietarioSearchAdapter.search(filtroDto, paginacao);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("turing@email.com", result.getContent().getFirst().getEmail());
        assertEquals("aturing", result.getContent().getFirst().getLogin());
    }

    @Test
    void devePesquisarProprietarioPorDescricao() {
        // Arrange
        var filtroDto = new UsuarioFiltroDto(
                null, null, null, null, "Proprietário principal");

        // Act
        Page<ProprietarioEntity> result = proprietarioSearchAdapter.search(filtroDto, paginacao);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Proprietário principal", result.getContent().getFirst().getDescricao());
        assertEquals("aturing!123", result.getContent().getFirst().getSenha());
    }

    @Test
    void deveRetornarPaginaVaziaQuandoFiltroNaoCorresponde() {
        // Arrange
        filtroDto = new UsuarioFiltroDto(
                null, "Nome Inexistente", null, null, null);

        // Act
        Page<ProprietarioEntity> result = proprietarioSearchAdapter.search(filtroDto, paginacao);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertTrue(result.getContent().isEmpty());
    }

    @Test
    void devePesquisarProprietarioPorCombinacaoDeNomeEEmail() {
        // Arrange
        var filtroDto = new UsuarioFiltroDto(
                null, "Alan Turing", "turing@email.com", null, null);

        // Act
        Page<ProprietarioEntity> result = proprietarioSearchAdapter.search(filtroDto, paginacao);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Alan Turing", result.getContent().getFirst().getNome());
        assertEquals("turing@email.com", result.getContent().getFirst().getEmail());
        assertEquals("aturing", result.getContent().getFirst().getLogin());
    }

    @Test
    void devePesquisarProprietarioPorValoresParciaisDeNomeEDescricao() {
        // Arrange
        var filtroDto = new UsuarioFiltroDto(
                null, "Alan", null, null, "principal");

        // Act
        Page<ProprietarioEntity> result = proprietarioSearchAdapter.search(filtroDto, paginacao);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Alan Turing", result.getContent().getFirst().getNome());
        assertEquals("Proprietário principal", result.getContent().getFirst().getDescricao());
    }
}

