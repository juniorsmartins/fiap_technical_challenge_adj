package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http500.RestauranteNonPersistenceException;
import br.com.fiap.tech.challenge_user.domain.model.enums.TipoCozinhaEnum;
import br.com.fiap.tech.challenge_user.infrastructure.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.infrastructure.entity.RestauranteEntity;
import br.com.fiap.tech.challenge_user.infrastructure.repository.ProprietarioRepository;
import br.com.fiap.tech.challenge_user.infrastructure.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RestauranteCreateAdapterTest {

    private final RestauranteRepository repository;

    private final ProprietarioRepository proprietarioRepository;

    private CreateOutputPort<RestauranteEntity> restauranteCreateAdapter;

    private EnderecoEntity enderecoEntity;

    private LocalTime horaAbertura;

    private LocalTime horaFechamento;

    private ProprietarioEntity proprietarioEntity;

    @Autowired
    RestauranteCreateAdapterTest(RestauranteRepository repository, ProprietarioRepository proprietarioRepository) {
        this.repository = repository;
        this.proprietarioRepository = proprietarioRepository;
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        proprietarioRepository.deleteAll();

        restauranteCreateAdapter = new RestauranteCreateAdapter(repository);

        enderecoEntity = new EnderecoEntity();
        enderecoEntity.setCep("01001-000");
        enderecoEntity.setLogradouro("Avenida Central");
        enderecoEntity.setNumero("1500");

        horaAbertura = LocalTime.of(8, 10, 10);
        horaFechamento = LocalTime.of(22, 0, 0);

        proprietarioEntity = new ProprietarioEntity();
        proprietarioEntity.setNome("João Silva");
        proprietarioEntity.setEmail("joao@email.com");
        proprietarioEntity.setLogin("jsilva");
        proprietarioEntity.setSenha("jsilva!123");
        proprietarioEntity.setDescricao("Proprietário principal");

        proprietarioRepository.save(proprietarioEntity);
    }

    @Test
    void deveSalvarRestauranteComSucesso() {
        // Arrange
        var restauranteEntity = new RestauranteEntity();
        restauranteEntity.setNome("Restaurante Sabor");
        restauranteEntity.setTipoCozinhaEnum(TipoCozinhaEnum.CARNIVORA);
        restauranteEntity.setHoraAbertura(horaAbertura);
        restauranteEntity.setHoraFechamento(horaFechamento);
        restauranteEntity.setEndereco(enderecoEntity);
        restauranteEntity.setProprietario(proprietarioEntity);

        // Act
        var savedEntity = restauranteCreateAdapter.save(restauranteEntity);

        // Assert
        assertNotNull(savedEntity);
        assertNotNull(savedEntity.getRestauranteId());
        assertEquals("Restaurante Sabor", savedEntity.getNome());
        assertEquals(TipoCozinhaEnum.CARNIVORA, savedEntity.getTipoCozinhaEnum());
        assertEquals(horaAbertura, savedEntity.getHoraAbertura());
        assertEquals(horaFechamento, savedEntity.getHoraFechamento());
        assertEquals(proprietarioEntity.getUsuarioId(), savedEntity.getProprietario().getUsuarioId());
        assertEquals(enderecoEntity.getEnderecoId(), savedEntity.getEndereco().getEnderecoId());

        // Verifica persistência no banco
        var persistedEntity = repository.findById(savedEntity.getRestauranteId());
        assertTrue(persistedEntity.isPresent());
        assertEquals(savedEntity.getNome(), persistedEntity.get().getNome());
    }

    @Test
    void deveLancarExcecaoQuandoNomeNulo() {
        // Arrange
        var restauranteEntity = new RestauranteEntity();
        restauranteEntity.setTipoCozinhaEnum(TipoCozinhaEnum.CARNIVORA);
        restauranteEntity.setHoraAbertura(horaAbertura);
        restauranteEntity.setHoraFechamento(horaFechamento);
        restauranteEntity.setEndereco(enderecoEntity);
        restauranteEntity.setProprietario(proprietarioEntity);

        // Act & Assert
        assertThrows(RestauranteNonPersistenceException.class, () -> restauranteCreateAdapter.save(restauranteEntity));
    }

    @Test
    void deveLancarExcecaoQuandoTipoCozinhaNulo() {
        // Arrange
        var restauranteEntity = new RestauranteEntity();
        restauranteEntity.setNome("Restaurante Sabor");
        restauranteEntity.setHoraAbertura(horaAbertura);
        restauranteEntity.setHoraFechamento(horaFechamento);
        restauranteEntity.setEndereco(enderecoEntity);
        restauranteEntity.setProprietario(proprietarioEntity);

        // Act & Assert
        assertThrows(RestauranteNonPersistenceException.class, () -> restauranteCreateAdapter.save(restauranteEntity));
    }

    @Test
    void deveLancarExcecaoQuandoHoraAberturaNulo() {
        // Arrange
        var restauranteEntity = new RestauranteEntity();
        restauranteEntity.setNome("Restaurante Sabor");
        restauranteEntity.setTipoCozinhaEnum(TipoCozinhaEnum.CARNIVORA);
        restauranteEntity.setHoraFechamento(horaFechamento);
        restauranteEntity.setEndereco(enderecoEntity);
        restauranteEntity.setProprietario(proprietarioEntity);

        // Act & Assert
        assertThrows(RestauranteNonPersistenceException.class, () -> restauranteCreateAdapter.save(restauranteEntity));
    }

    @Test
    void deveLancarExcecaoQuandoHoraFechamentoNulo() {
        // Arrange
        var restauranteEntity = new RestauranteEntity();
        restauranteEntity.setNome("Restaurante Sabor");
        restauranteEntity.setTipoCozinhaEnum(TipoCozinhaEnum.CARNIVORA);
        restauranteEntity.setHoraAbertura(horaAbertura);
        restauranteEntity.setEndereco(enderecoEntity);
        restauranteEntity.setProprietario(proprietarioEntity);

        // Act & Assert
        assertThrows(RestauranteNonPersistenceException.class, () -> restauranteCreateAdapter.save(restauranteEntity));
    }

    @Test
    void deveLancarExcecaoQuandoEnderecoNulo() {
        // Arrange
        var restauranteEntity = new RestauranteEntity();
        restauranteEntity.setNome("Restaurante Sabor");
        restauranteEntity.setTipoCozinhaEnum(TipoCozinhaEnum.CARNIVORA);
        restauranteEntity.setHoraAbertura(horaAbertura);
        restauranteEntity.setHoraFechamento(horaFechamento);
        restauranteEntity.setProprietario(proprietarioEntity);

        // Act & Assert
        assertThrows(RestauranteNonPersistenceException.class, () -> restauranteCreateAdapter.save(restauranteEntity));
    }

    @Test
    void deveLancarExcecaoQuandoProprietarioNulo() {
        // Arrange
        var restauranteEntity = new RestauranteEntity();
        restauranteEntity.setNome("Restaurante Sabor");
        restauranteEntity.setTipoCozinhaEnum(TipoCozinhaEnum.CARNIVORA);
        restauranteEntity.setHoraAbertura(horaAbertura);
        restauranteEntity.setHoraFechamento(horaFechamento);
        restauranteEntity.setEndereco(enderecoEntity);

        // Act & Assert
        assertThrows(RestauranteNonPersistenceException.class, () -> restauranteCreateAdapter.save(restauranteEntity));
    }

    @Test
    void deveGerarRestauranteIdAutomaticamente() {
        // Arrange
        var restauranteEntity = new RestauranteEntity();
        restauranteEntity.setNome("Restaurante Sabor");
        restauranteEntity.setTipoCozinhaEnum(TipoCozinhaEnum.CARNIVORA);
        restauranteEntity.setHoraAbertura(horaAbertura);
        restauranteEntity.setHoraFechamento(horaFechamento);
        restauranteEntity.setEndereco(enderecoEntity);
        restauranteEntity.setProprietario(proprietarioEntity);

        // Act
        var savedEntity = restauranteCreateAdapter.save(restauranteEntity);

        // Assert
        assertNotNull(savedEntity.getRestauranteId(), "O restauranteId deve ser gerado automaticamente");
        assertTrue(repository.existsById(savedEntity.getRestauranteId()), "A entidade deve estar persistida no banco");
    }
}

