package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.interfaces.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.domain.entities.enums.TipoCozinhaEnum;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.gateways.RestauranteDeleteGateway;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.EnderecoDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.RestauranteDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.EnderecoRepository;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.ProprietarioRepository;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RestauranteDeleteGatewayTest {

    private final RestauranteRepository restauranteRepository;

    private final ProprietarioRepository proprietarioRepository;

    private final EnderecoRepository enderecoRepository;

    private DeleteOutputPort<RestauranteDao> restauranteDeleteAdapter;

    private EnderecoDao enderecoDao;

    private ProprietarioDao proprietarioEntity;

    private LocalTime horaAbertura;

    private LocalTime horaFechamento;

    @Autowired
    RestauranteDeleteGatewayTest(RestauranteRepository restauranteRepository,
                                 ProprietarioRepository proprietarioRepository,
                                 EnderecoRepository enderecoRepository) {
        this.restauranteRepository = restauranteRepository;
        this.proprietarioRepository = proprietarioRepository;
        this.enderecoRepository = enderecoRepository;
    }

    @BeforeEach
    void setUp() {
        restauranteRepository.deleteAll();
        proprietarioRepository.deleteAll();

        proprietarioEntity = new ProprietarioDao();
        proprietarioEntity.setNome("João Silva");
        proprietarioEntity.setEmail("joao@email.com");
        proprietarioEntity.setLogin("jsilva");
        proprietarioEntity.setSenha("jsilva!123");
        proprietarioEntity.setDescricao("Proprietário principal");

        proprietarioRepository.save(proprietarioEntity);

        restauranteDeleteAdapter = new RestauranteDeleteGateway(restauranteRepository);

        enderecoDao = new EnderecoDao();
        enderecoDao.setCep("01001-000");
        enderecoDao.setLogradouro("Avenida Central");
        enderecoDao.setNumero("1500");

        horaAbertura = LocalTime.of(8, 10, 10);
        horaFechamento = LocalTime.of(22, 0, 0);
    }

    @Test
    void deveDeletarRestauranteComSucesso() {
        // Arrange
        var restauranteEntity = new RestauranteDao();
        restauranteEntity.setNome("Restaurante Sabor");
        restauranteEntity.setTipoCozinhaEnum(TipoCozinhaEnum.CARNIVORA);
        restauranteEntity.setHoraAbertura(horaAbertura);
        restauranteEntity.setHoraFechamento(horaFechamento);
        restauranteEntity.setEndereco(enderecoDao);
        restauranteEntity.setProprietario(proprietarioEntity);
        var savedEntity = restauranteRepository.save(restauranteEntity);

        // Act
        restauranteDeleteAdapter.delete(savedEntity);

        // Assert
        var persistedEntity = restauranteRepository.findById(savedEntity.getRestauranteId());
        assertFalse(persistedEntity.isPresent(), "O restaurante deve ser removido do banco");
    }

    @Test
    void deveIgnorarDelecaoDeRestauranteNaoPersistido() {
        // Arrange
        var restauranteEntity = new RestauranteDao();
        restauranteEntity.setNome("Restaurante Sabor");
        restauranteEntity.setTipoCozinhaEnum(TipoCozinhaEnum.CARNIVORA);
        restauranteEntity.setHoraAbertura(horaAbertura);
        restauranteEntity.setHoraFechamento(horaFechamento);
        restauranteEntity.setEndereco(enderecoDao);
        restauranteEntity.setProprietario(proprietarioEntity);

        // Act
        restauranteDeleteAdapter.delete(restauranteEntity);

        // Assert
        assertEquals(0, restauranteRepository.count(), "O banco deve permanecer vazio");
    }

    @Test
    void deveDeletarRestauranteEEnderecoSemAfetarProprietario() {
        // Arrange
        var restauranteEntity = new RestauranteDao();
        restauranteEntity.setNome("Restaurante Sabor");
        restauranteEntity.setTipoCozinhaEnum(TipoCozinhaEnum.CARNIVORA);
        restauranteEntity.setHoraAbertura(horaAbertura);
        restauranteEntity.setHoraFechamento(horaFechamento);
        restauranteEntity.setEndereco(enderecoDao);
        restauranteEntity.setProprietario(proprietarioEntity);
        var savedEntity = restauranteRepository.save(restauranteEntity);

        // Act
        restauranteDeleteAdapter.delete(savedEntity);

        // Assert
        var persistedRestaurante = restauranteRepository.findById(savedEntity.getRestauranteId());
        assertFalse(persistedRestaurante.isPresent(), "O restaurante deve ser removido do banco.");

        var persistedEndereco = enderecoRepository.findById(savedEntity.getEndereco().getEnderecoId());
        assertFalse(persistedEndereco.isPresent(), "O endereço deve ser removido do banco.");

        var persistedProprietario = proprietarioRepository.findById(savedEntity.getProprietario().getUsuarioId());
        assertTrue(persistedProprietario.isPresent(), "O proprietário não deve ser afetado.");
        assertEquals("João Silva", persistedProprietario.get().getNome());
    }
}

