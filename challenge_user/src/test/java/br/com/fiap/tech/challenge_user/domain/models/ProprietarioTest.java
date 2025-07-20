package br.com.fiap.tech.challenge_user.domain.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProprietarioTest {

    private UUID usuarioId;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private Endereco endereco;
    private String descricao;

    @BeforeEach
    void setUp() {
        usuarioId = UUID.randomUUID();
        nome = "João Silva";
        email = "joao@email.com";
        login = "jsilva";
        senha = "jsilva!123";
        descricao = "Proprietário principal";

        endereco = new Endereco();
        endereco.setEnderecoId(UUID.randomUUID());
        endereco.setCep("01001-000");
        endereco.setLogradouro("Avenida Central");
        endereco.setNumero("1500");
    }

    @Test
    void deveCriarProprietarioComConstrutorPadrao() {
        // Act
        var proprietario = new Proprietario();

        // Assert
        assertThat(proprietario.getUsuarioId()).isNull();
        assertThat(proprietario.getNome()).isNull();
        assertThat(proprietario.getEmail()).isNull();
        assertThat(proprietario.getLogin()).isNull();
        assertThat(proprietario.getSenha()).isNull();
        assertThat(proprietario.getEndereco()).isNull();
        assertThat(proprietario.getDescricao()).isNull();
    }

    @Test
    void deveCriarProprietarioComConstrutorCompleto() {
        // Act
        var proprietario = new Proprietario(usuarioId, nome, email, login, senha, endereco, descricao);

        // Assert
        assertThat(proprietario.getUsuarioId()).isEqualTo(usuarioId);
        assertThat(proprietario.getNome()).isEqualTo(nome);
        assertThat(proprietario.getEmail()).isEqualTo(email);
        assertThat(proprietario.getLogin()).isEqualTo(login);
        assertThat(proprietario.getSenha()).isEqualTo(senha);
        assertThat(proprietario.getEndereco()).isEqualTo(endereco);
        assertThat(proprietario.getDescricao()).isEqualTo(descricao);
    }

    @Test
    void deveCriarProprietarioComConstrutorSemUsuarioId() {
        // Act
        var proprietario = new Proprietario(nome, email, login, senha, endereco, descricao);

        // Assert
        assertThat(proprietario.getUsuarioId()).isNull();
        assertThat(proprietario.getNome()).isEqualTo(nome);
        assertThat(proprietario.getEmail()).isEqualTo(email);
        assertThat(proprietario.getLogin()).isEqualTo(login);
        assertThat(proprietario.getSenha()).isEqualTo(senha);
        assertThat(proprietario.getEndereco()).isEqualTo(endereco);
        assertThat(proprietario.getDescricao()).isEqualTo(descricao);
    }

    @Test
    void devePermitirDescricaoNula() {
        // Act
        var proprietario = new Proprietario(usuarioId, nome, email, login, senha, endereco, null);

        // Assert
        assertThat(proprietario.getUsuarioId()).isEqualTo(usuarioId);
        assertThat(proprietario.getNome()).isEqualTo(nome);
        assertThat(proprietario.getEmail()).isEqualTo(email);
        assertThat(proprietario.getLogin()).isEqualTo(login);
        assertThat(proprietario.getSenha()).isEqualTo(senha);
        assertThat(proprietario.getEndereco()).isEqualTo(endereco);
        assertThat(proprietario.getDescricao()).isNull();
    }

    @Test
    void devePermitirEnderecoNulo() {
        // Act
        var proprietario = new Proprietario(usuarioId, nome, email, login, senha, null, descricao);

        // Assert
        assertThat(proprietario.getUsuarioId()).isEqualTo(usuarioId);
        assertThat(proprietario.getNome()).isEqualTo(nome);
        assertThat(proprietario.getEmail()).isEqualTo(email);
        assertThat(proprietario.getLogin()).isEqualTo(login);
        assertThat(proprietario.getSenha()).isEqualTo(senha);
        assertThat(proprietario.getEndereco()).isNull();
        assertThat(proprietario.getDescricao()).isEqualTo(descricao);
    }
}

