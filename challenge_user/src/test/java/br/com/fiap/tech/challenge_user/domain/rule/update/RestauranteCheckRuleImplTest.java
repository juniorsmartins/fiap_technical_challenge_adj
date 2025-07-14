package br.com.fiap.tech.challenge_user.domain.rule.update;

import br.com.fiap.tech.challenge_user.infrastructure.presenters.EntityMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.ProprietarioNotFoundException;
import br.com.fiap.tech.challenge_user.domain.model.Proprietario;
import br.com.fiap.tech.challenge_user.domain.model.Restaurante;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteCheckRuleImplTest {

    @Mock
    private FindByIdOutputPort<ProprietarioEntity> findByIdOutputPort;

    @Mock
    private EntityMapper<Proprietario, ProprietarioEntity> mapper;

    @InjectMocks
    private RestauranteCheckRuleImpl restauranteCheckRule;

    private UUID proprietarioId;

    private Restaurante restaurante;

    private ProprietarioEntity proprietarioEntity;

    private Proprietario proprietarioDomain;

    @BeforeEach
    void setUp() {
        proprietarioId = UUID.randomUUID();
        proprietarioEntity = new ProprietarioEntity();
        proprietarioEntity.setUsuarioId(proprietarioId);
        proprietarioEntity.setNome("Mads Torgersen");

        proprietarioDomain = new Proprietario();
        proprietarioDomain.setUsuarioId(proprietarioId);
        proprietarioDomain.setNome("Mads Torgersen");

        restaurante = new Restaurante();
        restaurante.setProprietario(proprietarioDomain);
    }

    @Test
    void deveRetornarProprietarioEntityQuandoChecarComSucesso() {
        // Arrange
        when(findByIdOutputPort.findById(proprietarioId)).thenReturn(Optional.of(proprietarioEntity));
        when(mapper.toDomain(proprietarioEntity)).thenReturn(proprietarioDomain);

        // Act
        ProprietarioEntity result = restauranteCheckRule.checkProprietario(restaurante);

        // Assert
        assertNotNull(result);
        assertEquals(proprietarioEntity, result);
        assertEquals(proprietarioDomain, restaurante.getProprietario());
        verify(findByIdOutputPort).findById(proprietarioId);
        verify(mapper).toDomain(proprietarioEntity);
        verifyNoMoreInteractions(findByIdOutputPort, mapper);
    }

    @Test
    void deveLancarProprietarioNotFoundExceptionQuandoNaoEncontrar() {
        // Arrange
        when(findByIdOutputPort.findById(proprietarioId)).thenReturn(Optional.empty());

        // Act & Assert
        ProprietarioNotFoundException exception = assertThrows(
                ProprietarioNotFoundException.class,
                () -> restauranteCheckRule.checkProprietario(restaurante)
        );
        assertEquals(proprietarioId, exception.getId());
        verify(findByIdOutputPort).findById(proprietarioId);
        verifyNoInteractions(mapper);
        verifyNoMoreInteractions(findByIdOutputPort);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoProprietarioEhNulo() {
        // Arrange
        restaurante.setProprietario(null);

        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> restauranteCheckRule.checkProprietario(restaurante)
        );
        verifyNoInteractions(findByIdOutputPort, mapper);
    }
}

