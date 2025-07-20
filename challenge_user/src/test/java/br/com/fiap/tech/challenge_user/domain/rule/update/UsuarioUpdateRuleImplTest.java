package br.com.fiap.tech.challenge_user.domain.rule.update;

import br.com.fiap.tech.challenge_user.domain.models.Endereco;
import br.com.fiap.tech.challenge_user.domain.models.Proprietario;
import br.com.fiap.tech.challenge_user.domain.rules.update.UsuarioUpdateRuleImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class UsuarioUpdateRuleImplTest {

    private UsuarioUpdateRuleImpl<Proprietario, ProprietarioDao> usuarioUpdateRule;

    private Proprietario proprietarioDomain;

    private ProprietarioDao proprietarioEntity;

    private EnderecoDao enderecoDao;

    private MockedStatic<BeanUtils> beanUtilsMock;

    @BeforeEach
    void setUp() {
        usuarioUpdateRule = new UsuarioUpdateRuleImpl<>();

        Endereco enderecoDomain = new Endereco();
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
        proprietarioDomain.setEndereco(enderecoDomain);

        proprietarioEntity = new ProprietarioDao();
        proprietarioEntity.setUsuarioId(proprietarioDomain.getUsuarioId());
        proprietarioEntity.setNome("Antigo");
        proprietarioEntity.setEmail("antigo@email.com");
        proprietarioEntity.setLogin("antigo");
        proprietarioEntity.setSenha("antiga123");
        proprietarioEntity.setDataHoraCriacao(new Date(0));
        proprietarioEntity.setDataHoraEdicao(new Date(0));
        proprietarioEntity.setEndereco(enderecoDao);
        proprietarioEntity.setDescricao("Descrição Antiga");

        // Mockar BeanUtils
        beanUtilsMock = mockStatic(BeanUtils.class);
    }

    @AfterEach
    void tearDown() {
        beanUtilsMock.close();
    }

    @Test
    void deveAtualizarUsuarioQuandoDomainEEntitySaoValidos() {
        // Arrange
        beanUtilsMock.when(() -> BeanUtils.copyProperties(any(), any(), eq("usuarioId"), eq("dataHoraCriacao"), eq("dataHoraEdicao"), eq("endereco")))
                .thenAnswer(invocation -> {
                    Proprietario source = invocation.getArgument(0);
                    ProprietarioDao target = invocation.getArgument(1);
                    target.setNome(source.getNome());
                    target.setEmail(source.getEmail());
                    target.setLogin(source.getLogin());
                    target.setSenha(source.getSenha());
                    target.setDescricao(source.getDescricao());
                    return null;
                });

        // Act
        ProprietarioDao result = usuarioUpdateRule.updateUser(proprietarioDomain, proprietarioEntity);

        // Assert
        assertNotNull(result);
        assertEquals(proprietarioDomain.getNome(), result.getNome());
        assertEquals(proprietarioDomain.getEmail(), result.getEmail());
        assertEquals(proprietarioDomain.getLogin(), result.getLogin());
        assertEquals(proprietarioDomain.getSenha(), result.getSenha());
        assertEquals(proprietarioDomain.getDescricao(), result.getDescricao());
        assertEquals(enderecoDao, result.getEndereco()); // Endereço não deve mudar
        assertEquals(proprietarioEntity.getUsuarioId(), result.getUsuarioId()); // usuarioId não deve mudar
        assertEquals(proprietarioEntity.getDataHoraCriacao(), result.getDataHoraCriacao()); // dataHoraCriacao não deve mudar
        assertEquals(proprietarioEntity.getDataHoraEdicao(), result.getDataHoraEdicao()); // dataHoraEdicao não deve mudar
        beanUtilsMock.verify(() -> BeanUtils.copyProperties(proprietarioDomain, proprietarioEntity, "usuarioId", "dataHoraCriacao", "dataHoraEdicao", "endereco"));
        beanUtilsMock.verifyNoMoreInteractions();
    }
}

