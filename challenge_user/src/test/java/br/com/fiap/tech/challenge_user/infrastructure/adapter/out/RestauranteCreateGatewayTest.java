package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http500.RestauranteNonPersistenceException;
import br.com.fiap.tech.challenge_user.domain.models.enums.TipoCozinhaEnum;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.gateways.RestauranteCreateGateway;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.EnderecoDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.RestauranteDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.ProprietarioRepository;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RestauranteCreateGatewayTest {

    private final RestauranteRepository repository;

    private final ProprietarioRepository proprietarioRepository;

    private CreateOutputPort<RestauranteDao> restauranteCreateAdapter;

    private EnderecoDao enderecoDao;

    private LocalTime horaAbertura;

    private LocalTime horaFechamento;

    private ProprietarioDao proprietarioEntity;

    @Autowired
    RestauranteCreateGatewayTest(RestauranteRepository repository, ProprietarioRepository proprietarioRepository) {
        this.repository = repository;
        this.proprietarioRepository = proprietarioRepository;
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        proprietarioRepository.deleteAll();

        restauranteCreateAdapter = new RestauranteCreateGateway(repository);

        enderecoDao = new EnderecoDao();
        enderecoDao.setCep("01001-000");
        enderecoDao.setLogradouro("Avenida Central");
        enderecoDao.setNumero("1500");

        horaAbertura = LocalTime.of(8, 10, 10);
        horaFechamento = LocalTime.of(22, 0, 0);

        proprietarioEntity = new ProprietarioDao();
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
        var restauranteEntity = new RestauranteDao();
        restauranteEntity.setNome("Restaurante Sabor");
        restauranteEntity.setTipoCozinhaEnum(TipoCozinhaEnum.CARNIVORA);
        restauranteEntity.setHoraAbertura(horaAbertura);
        restauranteEntity.setHoraFechamento(horaFechamento);
        restauranteEntity.setEndereco(enderecoDao);
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
        assertEquals(enderecoDao.getEnderecoId(), savedEntity.getEndereco().getEnderecoId());

        // Verifica persistência no banco
        var persistedEntity = repository.findById(savedEntity.getRestauranteId());
        assertTrue(persistedEntity.isPresent());
        assertEquals(savedEntity.getNome(), persistedEntity.get().getNome());
    }

    @Test
    void deveLancarExcecaoQuandoNomeNulo() {
        // Arrange
        var restauranteEntity = new RestauranteDao();
        restauranteEntity.setTipoCozinhaEnum(TipoCozinhaEnum.CARNIVORA);
        restauranteEntity.setHoraAbertura(horaAbertura);
        restauranteEntity.setHoraFechamento(horaFechamento);
        restauranteEntity.setEndereco(enderecoDao);
        restauranteEntity.setProprietario(proprietarioEntity);

        // Act & Assert
        assertThrows(RestauranteNonPersistenceException.class, () -> restauranteCreateAdapter.save(restauranteEntity));
    }

    @Test
    void deveLancarExcecaoQuandoTipoCozinhaNulo() {
        // Arrange
        var restauranteEntity = new RestauranteDao();
        restauranteEntity.setNome("Restaurante Sabor");
        restauranteEntity.setHoraAbertura(horaAbertura);
        restauranteEntity.setHoraFechamento(horaFechamento);
        restauranteEntity.setEndereco(enderecoDao);
        restauranteEntity.setProprietario(proprietarioEntity);

        // Act & Assert
        assertThrows(RestauranteNonPersistenceException.class, () -> restauranteCreateAdapter.save(restauranteEntity));
    }

    @Test
    void deveLancarExcecaoQuandoHoraAberturaNulo() {
        // Arrange
        var restauranteEntity = new RestauranteDao();
        restauranteEntity.setNome("Restaurante Sabor");
        restauranteEntity.setTipoCozinhaEnum(TipoCozinhaEnum.CARNIVORA);
        restauranteEntity.setHoraFechamento(horaFechamento);
        restauranteEntity.setEndereco(enderecoDao);
        restauranteEntity.setProprietario(proprietarioEntity);

        // Act & Assert
        assertThrows(RestauranteNonPersistenceException.class, () -> restauranteCreateAdapter.save(restauranteEntity));
    }

    @Test
    void deveLancarExcecaoQuandoHoraFechamentoNulo() {
        // Arrange
        var restauranteEntity = new RestauranteDao();
        restauranteEntity.setNome("Restaurante Sabor");
        restauranteEntity.setTipoCozinhaEnum(TipoCozinhaEnum.CARNIVORA);
        restauranteEntity.setHoraAbertura(horaAbertura);
        restauranteEntity.setEndereco(enderecoDao);
        restauranteEntity.setProprietario(proprietarioEntity);

        // Act & Assert
        assertThrows(RestauranteNonPersistenceException.class, () -> restauranteCreateAdapter.save(restauranteEntity));
    }

    @Test
    void deveLancarExcecaoQuandoEnderecoNulo() {
        // Arrange
        var restauranteEntity = new RestauranteDao();
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
        var restauranteEntity = new RestauranteDao();
        restauranteEntity.setNome("Restaurante Sabor");
        restauranteEntity.setTipoCozinhaEnum(TipoCozinhaEnum.CARNIVORA);
        restauranteEntity.setHoraAbertura(horaAbertura);
        restauranteEntity.setHoraFechamento(horaFechamento);
        restauranteEntity.setEndereco(enderecoDao);

        // Act & Assert
        assertThrows(RestauranteNonPersistenceException.class, () -> restauranteCreateAdapter.save(restauranteEntity));
    }

    @Test
    void deveGerarRestauranteIdAutomaticamente() {
        // Arrange
        var restauranteEntity = new RestauranteDao();
        restauranteEntity.setNome("Restaurante Sabor");
        restauranteEntity.setTipoCozinhaEnum(TipoCozinhaEnum.CARNIVORA);
        restauranteEntity.setHoraAbertura(horaAbertura);
        restauranteEntity.setHoraFechamento(horaFechamento);
        restauranteEntity.setEndereco(enderecoDao);
        restauranteEntity.setProprietario(proprietarioEntity);

        // Act
        var savedEntity = restauranteCreateAdapter.save(restauranteEntity);

        // Assert
        assertNotNull(savedEntity.getRestauranteId(), "O restauranteId deve ser gerado automaticamente");
        assertTrue(repository.existsById(savedEntity.getRestauranteId()), "A entidade deve estar persistida no banco");
    }
}

