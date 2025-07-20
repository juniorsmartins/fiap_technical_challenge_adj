package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.DaoPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteCreateUseCaseTest {

    @Mock
    private DaoPresenter<Restaurante, RestauranteDao> daoPresenter;

    @Mock
    private CreateOutputPort<RestauranteDao> createOutputPort;

    @Mock
    private RestauranteCheckRule restauranteCheckRule;

    @InjectMocks
    private RestauranteCreateUseCase restauranteCreateUseCase;

    private Restaurante restaurante;

    private RestauranteDao restauranteDao;

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

        var proprietarioEntity = new ProprietarioDao();
        proprietarioEntity.setUsuarioId(proprietarioId);
        proprietarioEntity.setNome("João");
        proprietarioEntity.setEmail("joao@email.com");
        proprietarioEntity.setLogin("joao");

        var enderecoEntity = new EnderecoDao();
        enderecoEntity.setCep("12345-678");
        enderecoEntity.setLogradouro("Rua Exemplo");
        enderecoEntity.setNumero("123");

        restauranteDao = new RestauranteDao();
        restauranteDao.setRestauranteId(restauranteId);
        restauranteDao.setNome("Restaurante Sabor");
        restauranteDao.setTipoCozinhaEnum(TipoCozinhaEnum.ITALIANA);
        restauranteDao.setHoraAbertura(LocalTime.of(11, 0));
        restauranteDao.setHoraFechamento(LocalTime.of(23, 0));
        restauranteDao.setEndereco(enderecoEntity);
        restauranteDao.setProprietario(proprietarioEntity);
    }

    @Test
    void deveCriarRestauranteQuandoValidacaoEhValida() {
        // Arrange
        when(daoPresenter.toEntity(restaurante)).thenReturn(restauranteDao);
        when(createOutputPort.save(restauranteDao)).thenReturn(restauranteDao);
        when(daoPresenter.toDomain(restauranteDao)).thenReturn(restaurante);

        // Act
        Restaurante result = restauranteCreateUseCase.create(restaurante);

        // Assert
        assertNotNull(result);
        assertEquals(restaurante, result);
        verify(restauranteCheckRule, times(1)).checkProprietario(restaurante);
        verify(daoPresenter, times(1)).toEntity(restaurante);
        verify(createOutputPort, times(1)).save(restauranteDao);
        verify(daoPresenter, times(1)).toDomain(restauranteDao);
        verifyNoMoreInteractions(restauranteCheckRule, daoPresenter, createOutputPort);
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
        verifyNoInteractions(daoPresenter, createOutputPort);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoRestauranteEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> restauranteCreateUseCase.create(null)
        );
        verifyNoInteractions(restauranteCheckRule, daoPresenter, createOutputPort);
    }
}

