package br.com.fiap.tech.challenge_user.domain.rule.update;

import br.com.fiap.tech.challenge_user.domain.models.Endereco;
import br.com.fiap.tech.challenge_user.domain.models.Proprietario;
import br.com.fiap.tech.challenge_user.domain.rules.update.EnderecoUpdateRuleImpl;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.EnderecoDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class EnderecoUpdateRuleImplTest {

    private EnderecoUpdateRuleImpl<Proprietario, ProprietarioDao> enderecoUpdateRule;

    private Proprietario proprietarioDomain;

    private ProprietarioDao proprietarioEntity;

    private Endereco enderecoDomain;

    private EnderecoDao enderecoDao;

    private MockedStatic<BeanUtils> beanUtilsMock;

    @BeforeEach
    void setUp() {
        enderecoUpdateRule = new EnderecoUpdateRuleImpl<>();

        enderecoDomain = new Endereco();
        enderecoDomain.setCep("12345-678");
        enderecoDomain.setLogradouro("Rua Teste");
        enderecoDomain.setNumero("123");

        enderecoDao = new EnderecoDao();
        enderecoDao.setCep("98765-432");
        enderecoDao.setLogradouro("Rua Antiga");
        enderecoDao.setNumero("456");
        enderecoDao.setEnderecoId(UUID.randomUUID());

        proprietarioDomain = new Proprietario();
        proprietarioDomain.setUsuarioId(UUID.randomUUID());
        proprietarioDomain.setNome("João");
        proprietarioDomain.setEmail("joao@email.com");
        proprietarioDomain.setLogin("joao");
        proprietarioDomain.setSenha("senha123");

        proprietarioEntity = new ProprietarioDao();
        proprietarioEntity.setUsuarioId(proprietarioDomain.getUsuarioId());
        proprietarioEntity.setNome("João");
        proprietarioEntity.setEmail("joao@email.com");
        proprietarioEntity.setLogin("joao");
        proprietarioEntity.setSenha("senha123");
        proprietarioEntity.setDataHoraCriacao(new Date());
        proprietarioEntity.setDataHoraEdicao(new Date());

        // Mockar BeanUtils
        beanUtilsMock = mockStatic(BeanUtils.class);
    }

    @AfterEach
    void tearDown() {
        beanUtilsMock.close();
    }

    @Test
    void deveRemoverEnderecoQuandoDomainSemEnderecoEEntityComEndereco() {
        // Arrange
        proprietarioDomain.setEndereco(null);
        proprietarioEntity.setEndereco(enderecoDao);

        // Act
        ProprietarioDao result = enderecoUpdateRule.updateAddress(proprietarioDomain, proprietarioEntity);

        // Assert
        assertNotNull(result);
        assertNull(result.getEndereco());
        beanUtilsMock.verifyNoInteractions();
    }

    @Test
    void deveCriarNovoEnderecoQuandoDomainComEnderecoEEntitySemEndereco() {
        // Arrange
        proprietarioDomain.setEndereco(enderecoDomain);
        proprietarioEntity.setEndereco(null);

        // Act
        ProprietarioDao result = enderecoUpdateRule.updateAddress(proprietarioDomain, proprietarioEntity);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getEndereco());
        assertEquals(enderecoDomain.getCep(), result.getEndereco().getCep());
        assertEquals(enderecoDomain.getLogradouro(), result.getEndereco().getLogradouro());
        assertEquals(enderecoDomain.getNumero(), result.getEndereco().getNumero());
        assertNull(result.getEndereco().getEnderecoId());
        beanUtilsMock.verifyNoInteractions();
    }

    @Test
    void deveAtualizarEnderecoQuandoDomainEEntityPossuemEndereco() {
        // Arrange
        proprietarioDomain.setEndereco(enderecoDomain);
        proprietarioEntity.setEndereco(enderecoDao);

        beanUtilsMock.when(() -> BeanUtils.copyProperties(any(), any(), eq("enderecoId")))
                .thenAnswer(invocation -> {
                    Endereco source = invocation.getArgument(0);
                    EnderecoDao target = invocation.getArgument(1);
                    target.setCep(source.getCep());
                    target.setLogradouro(source.getLogradouro());
                    target.setNumero(source.getNumero());
                    return null;
                });

        // Act
        ProprietarioDao result = enderecoUpdateRule.updateAddress(proprietarioDomain, proprietarioEntity);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getEndereco());
        assertEquals(enderecoDomain.getCep(), result.getEndereco().getCep());
        assertEquals(enderecoDomain.getLogradouro(), result.getEndereco().getLogradouro());
        assertEquals(enderecoDomain.getNumero(), result.getEndereco().getNumero());
        assertEquals(enderecoDao.getEnderecoId(), result.getEndereco().getEnderecoId());
        beanUtilsMock.verify(() -> BeanUtils.copyProperties(enderecoDomain, enderecoDao, "enderecoId"));
        beanUtilsMock.verifyNoMoreInteractions();
    }

    @Test
    void deveNaoFazerNadaQuandoDomainEEntitySemEndereco() {
        // Arrange
        proprietarioDomain.setEndereco(null);
        proprietarioEntity.setEndereco(null);

        // Act
        ProprietarioDao result = enderecoUpdateRule.updateAddress(proprietarioDomain, proprietarioEntity);

        // Assert
        assertNotNull(result);
        assertNull(result.getEndereco());
        beanUtilsMock.verifyNoInteractions();
    }
}

