package br.com.fiap.tech.challenge_user.application.usecase;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.DaoPresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.out.CreateOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http409.UsuarioNonUniqueNomeException;
import br.com.fiap.tech.challenge_user.domain.entities.Proprietario;
import br.com.fiap.tech.challenge_user.domain.rules.UsuarioRulesStrategy;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProprietarioCreateUseCaseTest {

    @Mock
    private DaoPresenter<Proprietario, ProprietarioDao> daoPresenter;

    @Mock
    private CreateOutputPort<ProprietarioDao> createOutputPort;

    @Mock
    private UsuarioRulesStrategy<Proprietario> rule1;

    @Mock
    private UsuarioRulesStrategy<Proprietario> rule2;

    @InjectMocks
    private ProprietarioCreateUseCase proprietarioCreateUseCase;

    private Proprietario proprietario;

    private ProprietarioDao proprietarioEntity;

    @BeforeEach
    void setUp() {
        UUID usuarioId = UUID.randomUUID();
        proprietario = new Proprietario();
        proprietario.setUsuarioId(usuarioId);
        proprietario.setNome("João");
        proprietario.setEmail("joao@email.com");
        proprietario.setLogin("joao");

        proprietarioEntity = new ProprietarioDao();
        proprietarioEntity.setUsuarioId(usuarioId);
        proprietarioEntity.setNome("João");
        proprietarioEntity.setEmail("joao@email.com");
        proprietarioEntity.setLogin("joao");

        List<UsuarioRulesStrategy<Proprietario>> rulesStrategy = List.of(rule1, rule2);
        proprietarioCreateUseCase = new ProprietarioCreateUseCase(daoPresenter, createOutputPort, rulesStrategy);
    }

    @Test
    void deveCriarProprietarioQuandoRegrasSaoValidas() {
        // Arrange
        when(daoPresenter.toEntity(proprietario)).thenReturn(proprietarioEntity);
        when(createOutputPort.save(proprietarioEntity)).thenReturn(proprietarioEntity);
        when(daoPresenter.toDomain(proprietarioEntity)).thenReturn(proprietario);

        // Act
        Proprietario result = proprietarioCreateUseCase.create(proprietario);

        // Assert
        assertNotNull(result);
        assertEquals(proprietario, result);
        verify(rule1, times(1)).executar(proprietario);
        verify(rule2, times(1)).executar(proprietario);
        verify(daoPresenter, times(1)).toEntity(proprietario);
        verify(createOutputPort, times(1)).save(proprietarioEntity);
        verify(daoPresenter, times(1)).toDomain(proprietarioEntity);
        verifyNoMoreInteractions(rule1, rule2, daoPresenter, createOutputPort);
    }

    @Test
    void deveLancarExcecaoQuandoRegraFalha() {
        // Arrange
        doThrow(new UsuarioNonUniqueNomeException("João")).when(rule1).executar(proprietario);

        // Act & Assert
        UsuarioNonUniqueNomeException exception = assertThrows(
                UsuarioNonUniqueNomeException.class,
                () -> proprietarioCreateUseCase.create(proprietario)
        );
        assertEquals("João", exception.getValor());
        verify(rule1, times(1)).executar(proprietario);
        verifyNoInteractions(rule2, daoPresenter, createOutputPort);
    }

    @Test
    void deveLancarNullPointerExceptionQuandoUsuarioEhNulo() {
        // Act & Assert
        assertThrows(
                NullPointerException.class,
                () -> proprietarioCreateUseCase.create(null)
        );
        verifyNoInteractions(rule1, rule2, daoPresenter, createOutputPort);
    }
}