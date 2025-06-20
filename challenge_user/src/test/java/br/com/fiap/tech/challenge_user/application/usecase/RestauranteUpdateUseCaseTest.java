package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.exception.http404.RestauranteNotFoundException;
import br.com.fiap.tech.challenge_user.domain.model.Endereco;
import br.com.fiap.tech.challenge_user.domain.model.Proprietario;
import br.com.fiap.tech.challenge_user.domain.model.Restaurante;
import br.com.fiap.tech.challenge_user.domain.model.enums.TipoCozinhaEnum;
import br.com.fiap.tech.challenge_user.domain.rule.update.RestauranteCheckRule;
import br.com.fiap.tech.challenge_user.infrastructure.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.infrastructure.entity.RestauranteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteUpdateUseCaseTest {

    @Mock
    private EntityMapper<Restaurante, RestauranteEntity> entityMapper;

    @Mock
    private CreateOutputPort<RestauranteEntity> createOutputPort;

    @Mock
    private FindByIdOutputPort<RestauranteEntity> findByIdOutputPort;

    @Mock
    private RestauranteCheckRule restauranteCheckRule;

    @InjectMocks
    private RestauranteUpdateUseCase restauranteUpdateUseCase;

    private Restaurante restaurante;

    private RestauranteEntity restauranteEntity;

    private Proprietario proprietario;

    private ProprietarioEntity proprietarioEntity;

    private Endereco endereco;

    private EnderecoEntity enderecoEntity;

    private UUID restauranteId;

    @BeforeEach
    void setUp() {
        restauranteId = UUID.randomUUID();
        UUID proprietarioId = UUID.randomUUID();

        proprietario = new Proprietario();
        proprietario.setUsuarioId(proprietarioId);
        proprietario.setNome("João");
        proprietario.setEmail("joao@email.com");
        proprietario.setLogin("joao");

        proprietarioEntity = new ProprietarioEntity();
        proprietarioEntity.setUsuarioId(proprietarioId);
        proprietarioEntity.setNome("João");
        proprietarioEntity.setEmail("joao@email.com");
        proprietarioEntity.setLogin("joao");

        endereco = new Endereco();
        endereco.setCep("12345-678");
        endereco.setLogradouro("Rua Exemplo");
        endereco.setNumero("123");

        enderecoEntity = new EnderecoEntity();
        enderecoEntity.setCep("12345-678");
        enderecoEntity.setLogradouro("Rua Exemplo");
        enderecoEntity.setNumero("123");

        restaurante = new Restaurante();
        restaurante.setRestauranteId(restauranteId);
        restaurante.setNome("Restaurante Sabor");
        restaurante.setTipoCozinhaEnum(TipoCozinhaEnum.ITALIANA);
        restaurante.setHoraAbertura(LocalTime.of(11, 0));
        restaurante.setHoraFechamento(LocalTime.of(23, 0));
        restaurante.setEndereco(endereco);
        restaurante.setProprietario(proprietario);

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
    void deveAtualizarRestauranteQuandoDadosSaoValidos() {
        // Arrange
        doReturn(Optional.of(restauranteEntity)).when(findByIdOutputPort).findById(restauranteId);
        doReturn(proprietarioEntity).when(restauranteCheckRule).checkProprietario(restaurante);
        doReturn(restauranteEntity).when(createOutputPort).save(restauranteEntity);
        doReturn(restaurante).when(entityMapper).toDomain(restauranteEntity);

        // Act
        Restaurante result = restauranteUpdateUseCase.update(restauranteId, restaurante);

        // Assert
        assertNotNull(result);
        assertEquals(restaurante, result);
        verify(findByIdOutputPort, times(1)).findById(restauranteId);
        verify(restauranteCheckRule, times(1)).checkProprietario(restaurante);
        verify(createOutputPort, times(1)).save(restauranteEntity);
        verify(entityMapper, times(1)).toDomain(restauranteEntity);
        verifyNoMoreInteractions(findByIdOutputPort, restauranteCheckRule, createOutputPort, entityMapper);
    }

    @Test
    void deveLancarRestauranteNotFoundExceptionQuandoRestauranteNaoEhEncontrado() {
        // Arrange
        doReturn(Optional.empty()).when(findByIdOutputPort).findById(restauranteId);

        // Act & Assert
        RestauranteNotFoundException exception = assertThrows(
                RestauranteNotFoundException.class,
                () -> restauranteUpdateUseCase.update(restauranteId, restaurante)
        );
        assertEquals(restauranteId, exception.getId());
        verify(findByIdOutputPort, times(1)).findById(restauranteId);
        verifyNoInteractions(restauranteCheckRule, createOutputPort, entityMapper);
        verifyNoMoreInteractions(findByIdOutputPort);
    }

    @Test
    void deveLancarExcecaoQuandoCheckProprietarioFalha() {
        // Arrange
        IllegalArgumentException exceptionToThrow = new IllegalArgumentException("Proprietário inválido");
        doReturn(Optional.of(restauranteEntity)).when(findByIdOutputPort).findById(restauranteId);
        doThrow(exceptionToThrow).when(restauranteCheckRule).checkProprietario(restaurante);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> restauranteUpdateUseCase.update(restauranteId, restaurante)
        );
        assertEquals("Proprietário inválido", exception.getMessage());
        verify(findByIdOutputPort, times(1)).findById(restauranteId);
        verify(restauranteCheckRule, times(1)).checkProprietario(restaurante);
        verifyNoInteractions(createOutputPort, entityMapper);
        verifyNoMoreInteractions(findByIdOutputPort, restauranteCheckRule);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoIdEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> restauranteUpdateUseCase.update(null, restaurante)
        );
        verifyNoInteractions(findByIdOutputPort, restauranteCheckRule, createOutputPort, entityMapper);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoRestauranteEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> restauranteUpdateUseCase.update(restauranteId, null)
        );
        verifyNoInteractions(findByIdOutputPort, restauranteCheckRule, createOutputPort, entityMapper);
    }
}

