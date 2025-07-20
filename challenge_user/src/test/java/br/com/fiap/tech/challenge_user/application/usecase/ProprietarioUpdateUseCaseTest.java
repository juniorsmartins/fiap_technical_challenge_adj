package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.EntityMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.RecursoNotFoundException;
import br.com.fiap.tech.challenge_user.domain.models.Proprietario;
import br.com.fiap.tech.challenge_user.domain.rules.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.domain.rules.update.EnderecoUpdateRule;
import br.com.fiap.tech.challenge_user.domain.rules.update.UsuarioUpdateRule;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProprietarioUpdateUseCaseTest {

    @Mock
    private EntityMapper<Proprietario, ProprietarioDao> entityMapper;

    @Mock
    private CreateOutputPort<ProprietarioDao> createOutputPort;

    @Mock
    private FindByIdOutputPort<ProprietarioDao> findByIdOutputPort;

    @Mock
    private UsuarioUpdateRule<Proprietario, ProprietarioDao> usuarioUpdateRule;

    @Mock
    private EnderecoUpdateRule<Proprietario, ProprietarioDao> enderecoUpdateRule;

    @Mock
    private UsuarioRulesStrategy<Proprietario> rule1;

    @Mock
    private UsuarioRulesStrategy<Proprietario> rule2;

    private ProprietarioUpdateUseCase proprietarioUpdateUseCase;

    private Proprietario proprietario;

    private ProprietarioDao proprietarioEntity;

    private UUID proprietarioId;

    @BeforeEach
    void setUp() {
        proprietarioId = UUID.randomUUID();
        proprietario = new Proprietario();
        proprietario.setUsuarioId(proprietarioId);
        proprietario.setNome("João");
        proprietario.setEmail("joao@email.com");
        proprietario.setLogin("joao");

        proprietarioEntity = new ProprietarioDao();
        proprietarioEntity.setUsuarioId(proprietarioId);
        proprietarioEntity.setNome("João");
        proprietarioEntity.setEmail("joao@email.com");
        proprietarioEntity.setLogin("joao");

        List<UsuarioRulesStrategy<Proprietario>> rulesStrategy = List.of(rule1, rule2);
        proprietarioUpdateUseCase = new ProprietarioUpdateUseCase(
                entityMapper,
                createOutputPort,
                findByIdOutputPort,
                usuarioUpdateRule,
                enderecoUpdateRule,
                rulesStrategy
        );
    }

    @Test
    void deveAtualizarProprietarioQuandoDadosSaoValidos() {
        // Arrange
        doReturn(Optional.of(proprietarioEntity)).when(findByIdOutputPort).findById(proprietarioId);
        doReturn(proprietarioEntity).when(usuarioUpdateRule).updateUser(proprietario, proprietarioEntity);
        doReturn(proprietarioEntity).when(enderecoUpdateRule).updateAddress(proprietario, proprietarioEntity);
        doReturn(proprietarioEntity).when(createOutputPort).save(proprietarioEntity);
        doReturn(proprietario).when(entityMapper).toDomain(proprietarioEntity);

        // Act
        Proprietario result = proprietarioUpdateUseCase.update(proprietarioId, proprietario);

        // Assert
        assertNotNull(result);
        assertEquals(proprietario, result);
        verify(rule1, times(1)).executar(proprietario);
        verify(rule2, times(1)).executar(proprietario);
        verify(findByIdOutputPort, times(1)).findById(proprietarioId);
        verify(usuarioUpdateRule, times(1)).updateUser(proprietario, proprietarioEntity);
        verify(enderecoUpdateRule, times(1)).updateAddress(proprietario, proprietarioEntity);
        verify(createOutputPort, times(1)).save(proprietarioEntity);
        verify(entityMapper, times(1)).toDomain(proprietarioEntity);
        verifyNoMoreInteractions(rule1, rule2, findByIdOutputPort, usuarioUpdateRule, enderecoUpdateRule, createOutputPort, entityMapper);
    }

    @Test
    void deveLancarUsuarioNotFoundExceptionQuandoProprietarioNaoEhEncontrado() {
        // Arrange
        when(findByIdOutputPort.findById(proprietarioId)).thenReturn(Optional.empty());

        // Act & Assert
        RecursoNotFoundException exception = assertThrows(
                RecursoNotFoundException.class,
                () -> proprietarioUpdateUseCase.update(proprietarioId, proprietario)
        );

        assertThat(exception.getId()).isEqualTo(proprietarioId);
        verify(findByIdOutputPort, times(1)).findById(proprietarioId);
        verifyNoInteractions(rule1, rule2, usuarioUpdateRule, enderecoUpdateRule, createOutputPort, entityMapper);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoIdEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> proprietarioUpdateUseCase.update(null, proprietario)
        );
        verifyNoInteractions(rule1, rule2, findByIdOutputPort, usuarioUpdateRule, enderecoUpdateRule, createOutputPort, entityMapper);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoUsuarioEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> proprietarioUpdateUseCase.update(proprietarioId, null)
        );
        verifyNoInteractions(rule1, rule2, findByIdOutputPort, usuarioUpdateRule, enderecoUpdateRule, createOutputPort, entityMapper);
    }
}

