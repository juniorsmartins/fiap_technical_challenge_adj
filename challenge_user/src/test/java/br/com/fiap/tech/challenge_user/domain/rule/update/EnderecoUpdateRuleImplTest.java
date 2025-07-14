package br.com.fiap.tech.challenge_user.domain.rule.update;

import br.com.fiap.tech.challenge_user.domain.models.Endereco;
import br.com.fiap.tech.challenge_user.domain.models.Proprietario;
import br.com.fiap.tech.challenge_user.domain.rules.update.EnderecoUpdateRuleImpl;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.EnderecoEntity;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.ProprietarioEntity;
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

    private EnderecoUpdateRuleImpl<Proprietario, ProprietarioEntity> enderecoUpdateRule;

    private Proprietario proprietarioDomain;

    private ProprietarioEntity proprietarioEntity;

    private Endereco enderecoDomain;

    private EnderecoEntity enderecoEntity;

    private MockedStatic<BeanUtils> beanUtilsMock;

    @BeforeEach
    void setUp() {
        enderecoUpdateRule = new EnderecoUpdateRuleImpl<>();

        enderecoDomain = new Endereco();
        enderecoDomain.setCep("12345-678");
        enderecoDomain.setLogradouro("Rua Teste");
        enderecoDomain.setNumero("123");

        enderecoEntity = new EnderecoEntity();
        enderecoEntity.setCep("98765-432");
        enderecoEntity.setLogradouro("Rua Antiga");
        enderecoEntity.setNumero("456");
        enderecoEntity.setEnderecoId(UUID.randomUUID());

        proprietarioDomain = new Proprietario();
        proprietarioDomain.setUsuarioId(UUID.randomUUID());
        proprietarioDomain.setNome("João");
        proprietarioDomain.setEmail("joao@email.com");
        proprietarioDomain.setLogin("joao");
        proprietarioDomain.setSenha("senha123");

        proprietarioEntity = new ProprietarioEntity();
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
        proprietarioEntity.setEndereco(enderecoEntity);

        // Act
        ProprietarioEntity result = enderecoUpdateRule.updateAddress(proprietarioDomain, proprietarioEntity);

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
        ProprietarioEntity result = enderecoUpdateRule.updateAddress(proprietarioDomain, proprietarioEntity);

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
        proprietarioEntity.setEndereco(enderecoEntity);

        beanUtilsMock.when(() -> BeanUtils.copyProperties(any(), any(), eq("enderecoId")))
                .thenAnswer(invocation -> {
                    Endereco source = invocation.getArgument(0);
                    EnderecoEntity target = invocation.getArgument(1);
                    target.setCep(source.getCep());
                    target.setLogradouro(source.getLogradouro());
                    target.setNumero(source.getNumero());
                    return null;
                });

        // Act
        ProprietarioEntity result = enderecoUpdateRule.updateAddress(proprietarioDomain, proprietarioEntity);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getEndereco());
        assertEquals(enderecoDomain.getCep(), result.getEndereco().getCep());
        assertEquals(enderecoDomain.getLogradouro(), result.getEndereco().getLogradouro());
        assertEquals(enderecoDomain.getNumero(), result.getEndereco().getNumero());
        assertEquals(enderecoEntity.getEnderecoId(), result.getEndereco().getEnderecoId());
        beanUtilsMock.verify(() -> BeanUtils.copyProperties(enderecoDomain, enderecoEntity, "enderecoId"));
        beanUtilsMock.verifyNoMoreInteractions();
    }

    @Test
    void deveNaoFazerNadaQuandoDomainEEntitySemEndereco() {
        // Arrange
        proprietarioDomain.setEndereco(null);
        proprietarioEntity.setEndereco(null);

        // Act
        ProprietarioEntity result = enderecoUpdateRule.updateAddress(proprietarioDomain, proprietarioEntity);

        // Assert
        assertNotNull(result);
        assertNull(result.getEndereco());
        beanUtilsMock.verifyNoInteractions();
    }
}

