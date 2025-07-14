package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.interfaces.out.DeleteOutputPort;
import br.com.fiap.tech.challenge_user.domain.models.enums.TipoCozinhaEnum;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.gateways.RestauranteDeleteAdapter;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.EnderecoEntity;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.RestauranteEntity;
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
class RestauranteDeleteAdapterTest {

    private final RestauranteRepository restauranteRepository;

    private final ProprietarioRepository proprietarioRepository;

    private final EnderecoRepository enderecoRepository;

    private DeleteOutputPort<RestauranteEntity> restauranteDeleteAdapter;

    private EnderecoEntity enderecoEntity;

    private ProprietarioEntity proprietarioEntity;

    private LocalTime horaAbertura;

    private LocalTime horaFechamento;

    @Autowired
    RestauranteDeleteAdapterTest(RestauranteRepository restauranteRepository,
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

        proprietarioEntity = new ProprietarioEntity();
        proprietarioEntity.setNome("João Silva");
        proprietarioEntity.setEmail("joao@email.com");
        proprietarioEntity.setLogin("jsilva");
        proprietarioEntity.setSenha("jsilva!123");
        proprietarioEntity.setDescricao("Proprietário principal");

        proprietarioRepository.save(proprietarioEntity);

        restauranteDeleteAdapter = new RestauranteDeleteAdapter(restauranteRepository);

        enderecoEntity = new EnderecoEntity();
        enderecoEntity.setCep("01001-000");
        enderecoEntity.setLogradouro("Avenida Central");
        enderecoEntity.setNumero("1500");

        horaAbertura = LocalTime.of(8, 10, 10);
        horaFechamento = LocalTime.of(22, 0, 0);
    }

    @Test
    void deveDeletarRestauranteComSucesso() {
        // Arrange
        var restauranteEntity = new RestauranteEntity();
        restauranteEntity.setNome("Restaurante Sabor");
        restauranteEntity.setTipoCozinhaEnum(TipoCozinhaEnum.CARNIVORA);
        restauranteEntity.setHoraAbertura(horaAbertura);
        restauranteEntity.setHoraFechamento(horaFechamento);
        restauranteEntity.setEndereco(enderecoEntity);
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
        var restauranteEntity = new RestauranteEntity();
        restauranteEntity.setNome("Restaurante Sabor");
        restauranteEntity.setTipoCozinhaEnum(TipoCozinhaEnum.CARNIVORA);
        restauranteEntity.setHoraAbertura(horaAbertura);
        restauranteEntity.setHoraFechamento(horaFechamento);
        restauranteEntity.setEndereco(enderecoEntity);
        restauranteEntity.setProprietario(proprietarioEntity);

        // Act
        restauranteDeleteAdapter.delete(restauranteEntity);

        // Assert
        assertEquals(0, restauranteRepository.count(), "O banco deve permanecer vazio");
    }

    @Test
    void deveDeletarRestauranteEEnderecoSemAfetarProprietario() {
        // Arrange
        var restauranteEntity = new RestauranteEntity();
        restauranteEntity.setNome("Restaurante Sabor");
        restauranteEntity.setTipoCozinhaEnum(TipoCozinhaEnum.CARNIVORA);
        restauranteEntity.setHoraAbertura(horaAbertura);
        restauranteEntity.setHoraFechamento(horaFechamento);
        restauranteEntity.setEndereco(enderecoEntity);
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

