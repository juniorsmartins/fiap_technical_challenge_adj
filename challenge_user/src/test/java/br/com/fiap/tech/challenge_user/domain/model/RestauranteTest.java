package br.com.fiap.tech.challenge_user.domain.model;

import br.com.fiap.tech.challenge_user.application.exception.http409.OpeningTimeLaterClosingTimeException;
import br.com.fiap.tech.challenge_user.domain.models.Endereco;
import br.com.fiap.tech.challenge_user.domain.models.Proprietario;
import br.com.fiap.tech.challenge_user.domain.models.Restaurante;
import br.com.fiap.tech.challenge_user.domain.models.enums.TipoCozinhaEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestauranteTest {

    private UUID restauranteId;

    private String nome;

    private TipoCozinhaEnum tipoCozinha;

    private LocalTime horaAbertura;

    private LocalTime horaFechamento;

    private Endereco endereco;

    private Proprietario proprietario;

    @BeforeEach
    void setUp() {
        restauranteId = UUID.randomUUID();
        nome = "Restaurante Sabor";
        tipoCozinha = TipoCozinhaEnum.ITALIANA;
        horaAbertura = LocalTime.of(11, 0, 0);
        horaFechamento = LocalTime.of(23, 0, 0);

        endereco = new Endereco();
        endereco.setLogradouro("Rua Exemplo");
        endereco.setNumero("123");
        endereco.setCep("12345-678");

        proprietario = new Proprietario();
        proprietario.setUsuarioId(UUID.randomUUID());
        proprietario.setNome("João");
        proprietario.setEmail("joao@email.com");
        proprietario.setLogin("joao");
    }

    @Test
    void deveCriarRestauranteComHorariosValidos() {
        // Act
        Restaurante restaurante = new Restaurante(
                restauranteId, nome, tipoCozinha, horaAbertura, horaFechamento, endereco, proprietario
        );

        // Assert
        assertEquals(restauranteId, restaurante.getRestauranteId());
        assertEquals(nome, restaurante.getNome());
        assertEquals(tipoCozinha, restaurante.getTipoCozinhaEnum());
        assertEquals(horaAbertura, restaurante.getHoraAbertura());
        assertEquals(horaFechamento, restaurante.getHoraFechamento());
        assertEquals(endereco, restaurante.getEndereco());
        assertEquals(proprietario, restaurante.getProprietario());
    }

    @Test
    void deveCriarRestauranteComHorariosIguais() {
        // Arrange
        horaFechamento = horaAbertura; // 11:00

        // Act
        Restaurante restaurante = new Restaurante(
                restauranteId, nome, tipoCozinha, horaAbertura, horaFechamento, endereco, proprietario
        );

        // Assert
        assertEquals(horaAbertura, restaurante.getHoraFechamento());
    }

    @Test
    void deveLancarExcecaoQuandoHoraFechamentoEhAnteriorNoConstrutor() {
        // Arrange
        horaFechamento = LocalTime.of(10, 0); // Antes de 11:00

        // Act & Assert
        OpeningTimeLaterClosingTimeException exception = assertThrows(
                OpeningTimeLaterClosingTimeException.class,
                () -> new Restaurante(restauranteId, nome, tipoCozinha, horaAbertura, horaFechamento, endereco, proprietario)
        );
        assertEquals("exception.conflict.opening-time-later-closing-time", exception.getMessage());
    }

    @Test
    void deveCriarRestauranteComHoraAberturaNula() {
        // Arrange
        horaAbertura = null;

        // Act
        Restaurante restaurante = new Restaurante(
                restauranteId, nome, tipoCozinha, horaAbertura, horaFechamento, endereco, proprietario
        );

        // Assert
        assertNull(restaurante.getHoraAbertura());
        assertEquals(horaFechamento, restaurante.getHoraFechamento());
    }

    @Test
    void deveCriarRestauranteComHoraFechamentoNula() {
        // Arrange
        horaFechamento = null;

        // Act
        Restaurante restaurante = new Restaurante(
                restauranteId, nome, tipoCozinha, horaAbertura, horaFechamento, endereco, proprietario
        );

        // Assert
        assertNull(restaurante.getHoraFechamento());
        assertEquals(horaAbertura, restaurante.getHoraAbertura());
    }

    @Test
    void deveDefinirHoraFechamentoValida() {
        // Arrange
        Restaurante restaurante = new Restaurante(
                restauranteId, nome, tipoCozinha, horaAbertura, horaFechamento, endereco, proprietario
        );
        LocalTime novaHoraFechamento = LocalTime.of(22, 0, 0);

        // Act
        restaurante.setHoraFechamento(novaHoraFechamento);

        // Assert
        assertEquals(novaHoraFechamento, restaurante.getHoraFechamento());
    }

    @Test
    void deveDefinirHoraFechamentoIgualHoraAbertura() {
        // Arrange
        Restaurante restaurante = new Restaurante(
                restauranteId, nome, tipoCozinha, horaAbertura, horaFechamento, endereco, proprietario
        );
        LocalTime novaHoraFechamento = horaAbertura; // 11:00

        // Act
        restaurante.setHoraFechamento(novaHoraFechamento);

        // Assert
        assertEquals(novaHoraFechamento, restaurante.getHoraFechamento());
    }

    @Test
    void deveLancarExcecaoQuandoHoraFechamentoEhAnteriorNoSetter() {
        // Arrange
        Restaurante restaurante = new Restaurante(
                restauranteId, nome, tipoCozinha, horaAbertura, horaFechamento, endereco, proprietario
        );
        LocalTime novaHoraFechamento = LocalTime.of(10, 0, 0); // Antes de 11:00

        // Act & Assert
        OpeningTimeLaterClosingTimeException exception = assertThrows(
                OpeningTimeLaterClosingTimeException.class,
                () -> restaurante.setHoraFechamento(novaHoraFechamento)
        );
        assertEquals(novaHoraFechamento.toString(), exception.getValor());
    }

    @Test
    void deveDefinirHoraFechamentoNula() {
        // Arrange
        Restaurante restaurante = new Restaurante(
                restauranteId, nome, tipoCozinha, horaAbertura, horaFechamento, endereco, proprietario
        );

        // Act
        restaurante.setHoraFechamento(null);

        // Assert
        assertNull(restaurante.getHoraFechamento());
    }

    @Test
    void deveDefinirHoraFechamentoComHoraAberturaNula() {
        // Arrange
        Restaurante restaurante = new Restaurante(
                restauranteId, nome, tipoCozinha, null, horaFechamento, endereco, proprietario
        );
        LocalTime novaHoraFechamento = LocalTime.of(22, 0, 0);

        // Act
        restaurante.setHoraFechamento(novaHoraFechamento);

        // Assert
        assertEquals(novaHoraFechamento, restaurante.getHoraFechamento());
    }
}

