package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.DaoPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.RecursoNotFoundException;
import br.com.fiap.tech.challenge_user.domain.entities.Endereco;
import br.com.fiap.tech.challenge_user.domain.entities.Proprietario;
import br.com.fiap.tech.challenge_user.domain.entities.Restaurante;
import br.com.fiap.tech.challenge_user.domain.entities.enums.TipoCozinhaEnum;
import br.com.fiap.tech.challenge_user.domain.rules.update.RestauranteCheckRule;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.EnderecoDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.RestauranteDao;
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
    private DaoPresenter<Restaurante, RestauranteDao> daoPresenter;

    @Mock
    private CreateOutputPort<RestauranteDao> createOutputPort;

    @Mock
    private FindByIdOutputPort<RestauranteDao> findByIdOutputPort;

    @Mock
    private RestauranteCheckRule restauranteCheckRule;

    @InjectMocks
    private RestauranteUpdateUseCase restauranteUpdateUseCase;

    private Restaurante restaurante;

    private RestauranteDao restauranteDao;

    private ProprietarioDao proprietarioEntity;

    private UUID restauranteId;

    @BeforeEach
    void setUp() {
        restauranteId = UUID.randomUUID();
        UUID proprietarioId = UUID.randomUUID();

        Proprietario proprietario = new Proprietario();
        proprietario.setUsuarioId(proprietarioId);
        proprietario.setNome("João");
        proprietario.setEmail("joao@email.com");
        proprietario.setLogin("joao");

        proprietarioEntity = new ProprietarioDao();
        proprietarioEntity.setUsuarioId(proprietarioId);
        proprietarioEntity.setNome("João");
        proprietarioEntity.setEmail("joao@email.com");
        proprietarioEntity.setLogin("joao");

        Endereco endereco = new Endereco();
        endereco.setCep("12345-678");
        endereco.setLogradouro("Rua Exemplo");
        endereco.setNumero("123");

        EnderecoDao enderecoDao = new EnderecoDao();
        enderecoDao.setCep("12345-678");
        enderecoDao.setLogradouro("Rua Exemplo");
        enderecoDao.setNumero("123");

        restaurante = new Restaurante();
        restaurante.setRestauranteId(restauranteId);
        restaurante.setNome("Restaurante Sabor");
        restaurante.setTipoCozinhaEnum(TipoCozinhaEnum.ITALIANA);
        restaurante.setHoraAbertura(LocalTime.of(11, 0));
        restaurante.setHoraFechamento(LocalTime.of(23, 0));
        restaurante.setEndereco(endereco);
        restaurante.setProprietario(proprietario);

        restauranteDao = new RestauranteDao();
        restauranteDao.setRestauranteId(restauranteId);
        restauranteDao.setNome("Restaurante Sabor");
        restauranteDao.setTipoCozinhaEnum(TipoCozinhaEnum.ITALIANA);
        restauranteDao.setHoraAbertura(LocalTime.of(11, 0));
        restauranteDao.setHoraFechamento(LocalTime.of(23, 0));
        restauranteDao.setEndereco(enderecoDao);
        restauranteDao.setProprietario(proprietarioEntity);
    }

    @Test
    void deveAtualizarRestauranteQuandoDadosSaoValidos() {
        // Arrange
        doReturn(Optional.of(restauranteDao)).when(findByIdOutputPort).findById(restauranteId);
        doReturn(proprietarioEntity).when(restauranteCheckRule).checkProprietario(restaurante);
        doReturn(restauranteDao).when(createOutputPort).save(restauranteDao);
        doReturn(restaurante).when(daoPresenter).toDomain(restauranteDao);

        // Act
        Restaurante result = restauranteUpdateUseCase.update(restauranteId, restaurante);

        // Assert
        assertNotNull(result);
        assertEquals(restaurante, result);
        verify(findByIdOutputPort, times(1)).findById(restauranteId);
        verify(restauranteCheckRule, times(1)).checkProprietario(restaurante);
        verify(createOutputPort, times(1)).save(restauranteDao);
        verify(daoPresenter, times(1)).toDomain(restauranteDao);
        verifyNoMoreInteractions(findByIdOutputPort, restauranteCheckRule, createOutputPort, daoPresenter);
    }

    @Test
    void deveLancarRestauranteNotFoundExceptionQuandoRestauranteNaoEhEncontrado() {
        // Arrange
        doReturn(Optional.empty()).when(findByIdOutputPort).findById(restauranteId);

        // Act & Assert
        RecursoNotFoundException exception = assertThrows(
                RecursoNotFoundException.class,
                () -> restauranteUpdateUseCase.update(restauranteId, restaurante)
        );

        assertEquals(restauranteId, exception.getId());
        verify(findByIdOutputPort, times(1)).findById(restauranteId);
        verifyNoInteractions(restauranteCheckRule, createOutputPort, daoPresenter);
        verifyNoMoreInteractions(findByIdOutputPort);
    }

    @Test
    void deveLancarExcecaoQuandoCheckProprietarioFalha() {
        // Arrange
        IllegalArgumentException exceptionToThrow = new IllegalArgumentException("Proprietário inválido");
        doReturn(Optional.of(restauranteDao)).when(findByIdOutputPort).findById(restauranteId);
        doThrow(exceptionToThrow).when(restauranteCheckRule).checkProprietario(restaurante);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> restauranteUpdateUseCase.update(restauranteId, restaurante)
        );
        assertEquals("Proprietário inválido", exception.getMessage());
        verify(findByIdOutputPort, times(1)).findById(restauranteId);
        verify(restauranteCheckRule, times(1)).checkProprietario(restaurante);
        verifyNoInteractions(createOutputPort, daoPresenter);
        verifyNoMoreInteractions(findByIdOutputPort, restauranteCheckRule);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoIdEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> restauranteUpdateUseCase.update(null, restaurante)
        );
        verifyNoInteractions(findByIdOutputPort, restauranteCheckRule, createOutputPort, daoPresenter);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoRestauranteEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> restauranteUpdateUseCase.update(restauranteId, null)
        );
        verifyNoInteractions(findByIdOutputPort, restauranteCheckRule, createOutputPort, daoPresenter);
    }
}

