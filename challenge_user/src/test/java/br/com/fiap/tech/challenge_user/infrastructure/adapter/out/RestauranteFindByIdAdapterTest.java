package br.com.fiap.tech.challenge_user.infrastructure.adapter.out;

import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.model.enums.TipoCozinhaEnum;
import br.com.fiap.tech.challenge_user.infrastructure.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.infrastructure.entity.RestauranteEntity;
import br.com.fiap.tech.challenge_user.infrastructure.repository.EnderecoRepository;
import br.com.fiap.tech.challenge_user.infrastructure.repository.ProprietarioRepository;
import br.com.fiap.tech.challenge_user.infrastructure.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RestauranteFindByIdAdapterTest {

    private final RestauranteRepository restauranteRepository;

    private final ProprietarioRepository proprietarioRepository;

    private final EnderecoRepository enderecoRepository;

    private FindByIdOutputPort<RestauranteEntity> restauranteFindByIdAdapter;

    private EnderecoEntity enderecoEntity;

    private ProprietarioEntity proprietarioEntity;

    private LocalTime horaAbertura;

    private LocalTime horaFechamento;

    @Autowired
    RestauranteFindByIdAdapterTest(RestauranteRepository restauranteRepository,
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
        enderecoRepository.deleteAll();

        restauranteFindByIdAdapter = new RestauranteFindByIdAdapter(restauranteRepository);

        enderecoEntity = new EnderecoEntity();
        enderecoEntity.setCep("01001-000");
        enderecoEntity.setLogradouro("Avenida Central");
        enderecoEntity.setNumero("1500");
        enderecoRepository.save(enderecoEntity);

        proprietarioEntity = new ProprietarioEntity();
        proprietarioEntity.setNome("João Silva");
        proprietarioEntity.setEmail("joao@email.com");
        proprietarioEntity.setLogin("jsilva");
        proprietarioEntity.setSenha("jsilva!123");
        proprietarioEntity.setDescricao("Proprietário principal");
        proprietarioRepository.save(proprietarioEntity);

        horaAbertura = LocalTime.of(8, 10, 10);
        horaFechamento = LocalTime.of(22, 0, 0);
    }

    @Test
    void deveEncontrarRestauranteExistentePorId() {
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
        var result = restauranteFindByIdAdapter.findById(savedEntity.getRestauranteId());

        // Assert
        assertTrue(result.isPresent(), "O restaurante deve ser encontrado");
        var foundEntity = result.get();
        assertEquals(savedEntity.getRestauranteId(), foundEntity.getRestauranteId());
        assertEquals("Restaurante Sabor", foundEntity.getNome());
        assertEquals(TipoCozinhaEnum.CARNIVORA, foundEntity.getTipoCozinhaEnum());
        assertEquals(horaAbertura, foundEntity.getHoraAbertura());
        assertEquals(horaFechamento, foundEntity.getHoraFechamento());
    }

    @Test
    void deveRetornarVazioParaRestauranteInexistente() {
        // Arrange
        var nonExistentId = UUID.randomUUID();

        // Act
        var result = restauranteFindByIdAdapter.findById(nonExistentId);

        // Assert
        assertFalse(result.isPresent(), "Não deve encontrar restaurante com ID inexistente");
    }

    @Test
    void deveCarregarRelacionamentosAoEncontrarRestaurante() {
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
        var result = restauranteFindByIdAdapter.findById(savedEntity.getRestauranteId());

        // Assert
        assertTrue(result.isPresent(), "O restaurante deve ser encontrado");
        var foundEntity = result.get();
        assertNotNull(foundEntity.getEndereco(), "O endereço deve estar carregado");
        assertEquals(enderecoEntity.getEnderecoId(), foundEntity.getEndereco().getEnderecoId());
        assertEquals("Avenida Central", foundEntity.getEndereco().getLogradouro());

        assertNotNull(foundEntity.getProprietario(), "O proprietário deve estar carregado");
        assertEquals(proprietarioEntity.getUsuarioId(), foundEntity.getProprietario().getUsuarioId());
        assertEquals("João Silva", foundEntity.getProprietario().getNome());
    }
}

