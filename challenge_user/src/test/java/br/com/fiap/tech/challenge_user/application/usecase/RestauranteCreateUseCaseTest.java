package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.EntityMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.domain.models.Endereco;
import br.com.fiap.tech.challenge_user.domain.models.Proprietario;
import br.com.fiap.tech.challenge_user.domain.models.Restaurante;
import br.com.fiap.tech.challenge_user.domain.models.enums.TipoCozinhaEnum;
import br.com.fiap.tech.challenge_user.domain.rules.update.RestauranteCheckRule;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.EnderecoEntity;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.RestauranteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteCreateUseCaseTest {

    @Mock
    private EntityMapper<Restaurante, RestauranteEntity> entityMapper;

    @Mock
    private CreateOutputPort<RestauranteEntity> createOutputPort;

    @Mock
    private RestauranteCheckRule restauranteCheckRule;

    @InjectMocks
    private RestauranteCreateUseCase restauranteCreateUseCase;

    private Restaurante restaurante;

    private RestauranteEntity restauranteEntity;

    @BeforeEach
    void setUp() {
        UUID restauranteId = UUID.randomUUID();
        UUID proprietarioId = UUID.randomUUID();

        var proprietario = new Proprietario();
        proprietario.setUsuarioId(proprietarioId);
        proprietario.setNome("João");
        proprietario.setEmail("joao@email.com");
        proprietario.setLogin("joao");

        var endereco = new Endereco();
        endereco.setCep("12345-678");
        endereco.setLogradouro("Rua Exemplo");
        endereco.setNumero("123");

        restaurante = new Restaurante();
        restaurante.setRestauranteId(restauranteId);
        restaurante.setNome("Restaurante Sabor");
        restaurante.setTipoCozinhaEnum(TipoCozinhaEnum.ITALIANA);
        restaurante.setHoraAbertura(LocalTime.of(11, 0));
        restaurante.setHoraFechamento(LocalTime.of(23, 0));
        restaurante.setEndereco(endereco);
        restaurante.setProprietario(proprietario);

        var proprietarioEntity = new ProprietarioEntity();
        proprietarioEntity.setUsuarioId(proprietarioId);
        proprietarioEntity.setNome("João");
        proprietarioEntity.setEmail("joao@email.com");
        proprietarioEntity.setLogin("joao");

        var enderecoEntity = new EnderecoEntity();
        enderecoEntity.setCep("12345-678");
        enderecoEntity.setLogradouro("Rua Exemplo");
        enderecoEntity.setNumero("123");

        restauranteEntity = new RestauranteEntity();
        restauranteEntity.setRestauranteId(restauranteId);
        restauranteEntity.setNome("Restaurante Sabor");
        restauranteEntity.setTipoCozinhaEnum(TipoCozinhaEnum.ITALIANA);
        restauranteEntity.setHoraAbertura(LocalTime.of(11, 0));
        restauranteEntity.setHoraFechamento(LocalTime.of(23, 0));
        restauranteEntity.setEndereco(enderecoEntity);
        restauranteEntity.setProprietario(proprietarioEntity);
    }

    @Test
    void deveCriarRestauranteQuandoValidacaoEhValida() {
        // Arrange
        when(entityMapper.toEntity(restaurante)).thenReturn(restauranteEntity);
        when(createOutputPort.save(restauranteEntity)).thenReturn(restauranteEntity);
        when(entityMapper.toDomain(restauranteEntity)).thenReturn(restaurante);

        // Act
        Restaurante result = restauranteCreateUseCase.create(restaurante);

        // Assert
        assertNotNull(result);
        assertEquals(restaurante, result);
        verify(restauranteCheckRule, times(1)).checkProprietario(restaurante);
        verify(entityMapper, times(1)).toEntity(restaurante);
        verify(createOutputPort, times(1)).save(restauranteEntity);
        verify(entityMapper, times(1)).toDomain(restauranteEntity);
        verifyNoMoreInteractions(restauranteCheckRule, entityMapper, createOutputPort);
    }

    @Test
    void deveLancarExcecaoQuandoValidacaoFalha() {
        // Arrange
        doThrow(new IllegalArgumentException("Proprietário inválido"))
                .when(restauranteCheckRule).checkProprietario(restaurante);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> restauranteCreateUseCase.create(restaurante)
        );
        assertEquals("Proprietário inválido", exception.getMessage());
        verify(restauranteCheckRule, times(1)).checkProprietario(restaurante);
        verifyNoInteractions(entityMapper, createOutputPort);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoRestauranteEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> restauranteCreateUseCase.create(null)
        );
        verifyNoInteractions(restauranteCheckRule, entityMapper, createOutputPort);
    }
}

