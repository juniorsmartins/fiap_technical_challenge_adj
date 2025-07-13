package br.com.fiap.tech.challenge_user.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class EnderecoTest {

    private UUID enderecoId;
    private String cep;
    private String logradouro;
    private String numero;

    @BeforeEach
    void setUp() {
        enderecoId = UUID.randomUUID();
        cep = "01001-000";
        logradouro = "Avenida Central";
        numero = "1500";
    }

    @Test
    void deveCriarEnderecoComConstrutorPadrao() {
        // Act
        var endereco = new Endereco();

        // Assert
        assertThat(endereco.getEnderecoId()).isNull();
        assertThat(endereco.getCep()).isNull();
        assertThat(endereco.getLogradouro()).isNull();
        assertThat(endereco.getNumero()).isNull();
    }

    @Test
    void deveDefinirEObterValoresComGettersESetters() {
        // Arrange
        var endereco = new Endereco();

        // Act
        endereco.setEnderecoId(enderecoId);
        endereco.setCep(cep);
        endereco.setLogradouro(logradouro);
        endereco.setNumero(numero);

        // Assert
        assertThat(endereco.getEnderecoId()).isEqualTo(enderecoId);
        assertThat(endereco.getCep()).isEqualTo(cep);
        assertThat(endereco.getLogradouro()).isEqualTo(logradouro);
        assertThat(endereco.getNumero()).isEqualTo(numero);
    }

    @Test
    void deveSerIgualQuandoEnderecoIdForIgual() {
        // Arrange
        var endereco1 = new Endereco();
        endereco1.setEnderecoId(enderecoId);
        endereco1.setCep("01001-000");
        endereco1.setLogradouro("Avenida Central");
        endereco1.setNumero("1500");

        var endereco2 = new Endereco();
        endereco2.setEnderecoId(enderecoId);
        endereco2.setCep("02002-000");
        endereco2.setLogradouro("Rua Secundária");
        endereco2.setNumero("200");

        // Act & Assert
        assertThat(endereco1).isEqualTo(endereco2);
        assertThat(endereco1.hashCode()).isEqualTo(endereco2.hashCode());
    }

    @Test
    void deveSerDiferenteQuandoEnderecoIdForDiferente() {
        // Arrange
        var endereco1 = new Endereco();
        endereco1.setEnderecoId(UUID.randomUUID());
        endereco1.setCep(cep);
        endereco1.setLogradouro(logradouro);
        endereco1.setNumero(numero);

        var endereco2 = new Endereco();
        endereco2.setEnderecoId(UUID.randomUUID());
        endereco2.setCep(cep);
        endereco2.setLogradouro(logradouro);
        endereco2.setNumero(numero);

        // Act & Assert
        assertThat(endereco1).isNotEqualTo(endereco2);
        assertThat(endereco1.hashCode()).isNotEqualTo(endereco2.hashCode());
    }

    @Test
    void deveSerDiferenteQuandoComparadoComOutroTipoOuNulo() {
        // Arrange
        var endereco = new Endereco();
        endereco.setEnderecoId(enderecoId);

        // Act & Assert
        assertThat(endereco).isNotEqualTo(null);
        assertThat(endereco).isNotEqualTo(new Object());
    }

    @Test
    void deveSerIgualQuandoEnderecoIdForNuloEmAmbos() {
        // Arrange
        var endereco1 = new Endereco();
        var endereco2 = new Endereco();

        // Act & Assert
        assertThat(endereco1).isEqualTo(endereco2);
        assertThat(endereco1.hashCode()).isEqualTo(endereco2.hashCode());
    }

    @Test
    void devePermitirCamposNulos() {
        // Arrange
        var endereco = new Endereco();

        // Act
        endereco.setCep(null);
        endereco.setLogradouro(null);
        endereco.setNumero(null);

        // Assert
        assertThat(endereco.getCep()).isNull();
        assertThat(endereco.getLogradouro()).isNull();
        assertThat(endereco.getNumero()).isNull();
    }
}

