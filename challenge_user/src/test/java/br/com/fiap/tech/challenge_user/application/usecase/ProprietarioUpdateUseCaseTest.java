package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.application.mapper.EntityMapper;
import br.com.fiap.tech.challenge_user.application.port.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.exception.http404.UsuarioNotFoundException;
import br.com.fiap.tech.challenge_user.domain.exception.http409.UsuarioNonUniqueNomeException;
import br.com.fiap.tech.challenge_user.domain.model.Proprietario;
import br.com.fiap.tech.challenge_user.domain.rule.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.domain.rule.update.EnderecoUpdateRule;
import br.com.fiap.tech.challenge_user.domain.rule.update.UsuarioUpdateRule;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProprietarioUpdateUseCaseTest {

    @Mock
    private EntityMapper<Proprietario, ProprietarioEntity> entityMapper;

    @Mock
    private CreateOutputPort<ProprietarioEntity> createOutputPort;

    @Mock
    private FindByIdOutputPort<ProprietarioEntity> findByIdOutputPort;

    @Mock
    private UsuarioUpdateRule<Proprietario, ProprietarioEntity> usuarioUpdateRule;

    @Mock
    private EnderecoUpdateRule<Proprietario, ProprietarioEntity> enderecoUpdateRule;

    @Mock
    private UsuarioRulesStrategy<Proprietario> rule1;

    @Mock
    private UsuarioRulesStrategy<Proprietario> rule2;

    private ProprietarioUpdateUseCase proprietarioUpdateUseCase;

    private Proprietario proprietario;

    private ProprietarioEntity proprietarioEntity;

    private UUID proprietarioId;

    @BeforeEach
    void setUp() {
        proprietarioId = UUID.randomUUID();
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
        doReturn(Optional.empty()).when(findByIdOutputPort).findById(proprietarioId);

        // Act & Assert
        UsuarioNotFoundException exception = assertThrows(
                UsuarioNotFoundException.class,
                () -> proprietarioUpdateUseCase.update(proprietarioId, proprietario)
        );
        assertEquals(proprietarioId, exception.getId());
        verify(rule1, times(1)).executar(proprietario);
        verify(rule2, times(1)).executar(proprietario);
        verify(findByIdOutputPort, times(1)).findById(proprietarioId);
        verifyNoInteractions(usuarioUpdateRule, enderecoUpdateRule, createOutputPort, entityMapper);
        verifyNoMoreInteractions(rule1, rule2, findByIdOutputPort);
    }

    @Test
    void deveLancarExcecaoQuandoRegraFalha() {
        // Arrange
        doThrow(new UsuarioNonUniqueNomeException("João")).when(rule1).executar(proprietario);

        // Act & Assert
        UsuarioNonUniqueNomeException exception = assertThrows(
                UsuarioNonUniqueNomeException.class,
                () -> proprietarioUpdateUseCase.update(proprietarioId, proprietario)
        );
        assertEquals("João", exception.getValor());
        verify(rule1, times(1)).executar(proprietario);
        verifyNoInteractions(rule2, findByIdOutputPort, usuarioUpdateRule, enderecoUpdateRule, createOutputPort, entityMapper);
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

