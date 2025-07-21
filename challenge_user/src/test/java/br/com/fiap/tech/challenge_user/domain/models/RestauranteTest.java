package br.com.fiap.tech.challenge_user.domain.models;

import br.com.fiap.tech.challenge_user.application.exception.http409.AtributoObrigatorioException;
import br.com.fiap.tech.challenge_user.application.exception.http409.OpeningTimeLaterClosingTimeException;
import br.com.fiap.tech.challenge_user.domain.entities.Endereco;
import br.com.fiap.tech.challenge_user.domain.entities.Proprietario;
import br.com.fiap.tech.challenge_user.domain.entities.Restaurante;
import br.com.fiap.tech.challenge_user.domain.entities.enums.TipoCozinhaEnum;
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
        assertEquals(horaAbertura + " e " + novaHoraFechamento, exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoNomeEhNuloNoConstrutor() {
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> new Restaurante(restauranteId, null, tipoCozinha, horaAbertura, horaFechamento, endereco, proprietario)
        );
        assertEquals("nome", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoNomeEhEmBrancoNoConstrutor() {
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> new Restaurante(restauranteId, "   ", tipoCozinha, horaAbertura, horaFechamento, endereco, proprietario)
        );
        assertEquals("nome", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoTipoCozinhaEhNuloNoConstrutor() {
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> new Restaurante(restauranteId, nome, null, horaAbertura, horaFechamento, endereco, proprietario)
        );
        assertEquals("tipoCozinhaEnum", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoHoraAberturaEhNulaNoConstrutor() {
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> new Restaurante(restauranteId, nome, tipoCozinha, null, horaFechamento, endereco, proprietario)
        );
        assertEquals("horaAbertura", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoHoraFechamentoEhNulaNoConstrutor() {
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> new Restaurante(restauranteId, nome, tipoCozinha, horaAbertura, null, endereco, proprietario)
        );
        assertEquals("horaFechamento", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoEnderecoEhNuloNoConstrutor() {
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> new Restaurante(restauranteId, nome, tipoCozinha, horaAbertura, horaFechamento, null, proprietario)
        );
        assertEquals("endereco", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoProprietarioEhNuloNoConstrutor() {
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> new Restaurante(restauranteId, nome, tipoCozinha, horaAbertura, horaFechamento, endereco, null)
        );
        assertEquals("proprietario", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoNomeEhNuloNoSetter() {
        Restaurante restaurante = new Restaurante();
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> restaurante.setNome(null)
        );
        assertEquals("nome", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoNomeEhEmBrancoNoSetter() {
        Restaurante restaurante = new Restaurante();
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> restaurante.setNome("   ")
        );
        assertEquals("nome", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoTipoCozinhaEhNuloNoSetter() {
        Restaurante restaurante = new Restaurante();
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> restaurante.setTipoCozinhaEnum(null)
        );
        assertEquals("tipoCozinhaEnum", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoHoraAberturaEhNulaNoSetter() {
        Restaurante restaurante = new Restaurante(
                restauranteId, nome, tipoCozinha, horaAbertura, horaFechamento, endereco, proprietario
        );
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> restaurante.setHoraAbertura(null)
        );
        assertEquals("horaAbertura", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoEnderecoEhNuloNoSetter() {
        Restaurante restaurante = new Restaurante();
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> restaurante.setEndereco(null)
        );
        assertEquals("endereco", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoProprietarioEhNuloNoSetter() {
        Restaurante restaurante = new Restaurante();
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> restaurante.setProprietario(null)
        );
        assertEquals("proprietario", exception.getValor());
    }

    @Test
    void deveCriarRestauranteComConstrutorVazio() {
        Restaurante restaurante = new Restaurante();
        assertNull(restaurante.getRestauranteId());
        assertNull(restaurante.getNome());
        assertNull(restaurante.getTipoCozinhaEnum());
        assertNull(restaurante.getHoraAbertura());
        assertNull(restaurante.getHoraFechamento());
        assertNull(restaurante.getEndereco());
        assertNull(restaurante.getProprietario());
    }

    @Test
    void deveDefinirNomeValido() {
        Restaurante restaurante = new Restaurante();
        String novoNome = "Restaurante Novo";
        restaurante.setNome(novoNome);
        assertEquals(novoNome, restaurante.getNome());
    }

    @Test
    void deveDefinirTipoCozinhaValido() {
        Restaurante restaurante = new Restaurante();
        TipoCozinhaEnum novoTipoCozinha = TipoCozinhaEnum.BRASILEIRA;
        restaurante.setTipoCozinhaEnum(novoTipoCozinha);
        assertEquals(novoTipoCozinha, restaurante.getTipoCozinhaEnum());
    }

    @Test
    void deveDefinirHoraAberturaValida() {
        Restaurante restaurante = new Restaurante(
                restauranteId, nome, tipoCozinha, horaAbertura, horaFechamento, endereco, proprietario
        );
        LocalTime novaHoraAbertura = LocalTime.of(12, 0, 0);
        restaurante.setHoraAbertura(novaHoraAbertura);
        assertEquals(novaHoraAbertura, restaurante.getHoraAbertura());
    }

    @Test
    void deveLancarExcecaoQuandoHoraAberturaEhPosteriorNoSetter() {
        Restaurante restaurante = new Restaurante(
                restauranteId, nome, tipoCozinha, horaAbertura, horaFechamento, endereco, proprietario
        );
        LocalTime novaHoraAbertura = LocalTime.of(23, 30, 0); // Após horaFechamento (23:00)
        OpeningTimeLaterClosingTimeException exception = assertThrows(
                OpeningTimeLaterClosingTimeException.class,
                () -> restaurante.setHoraAbertura(novaHoraAbertura)
        );
        assertEquals(novaHoraAbertura + " e " + horaFechamento, exception.getValor());
    }

    @Test
    void deveDefinirHoraFechamentoValida() {
        Restaurante restaurante = new Restaurante(
                restauranteId, nome, tipoCozinha, horaAbertura, horaFechamento, endereco, proprietario
        );
        LocalTime novaHoraFechamento = LocalTime.of(22, 0, 0);
        restaurante.setHoraFechamento(novaHoraFechamento);
        assertEquals(novaHoraFechamento, restaurante.getHoraFechamento());
    }

    @Test
    void deveRetornarVerdadeiroParaRestaurantesIguais() {
        Restaurante restaurante1 = new Restaurante(
                restauranteId, nome, tipoCozinha, horaAbertura, horaFechamento, endereco, proprietario
        );
        Restaurante restaurante2 = new Restaurante(
                restauranteId, "Outro Nome", tipoCozinha, horaAbertura, horaFechamento, endereco, proprietario
        );
        assertTrue(restaurante1.equals(restaurante2));
        assertEquals(restaurante1.hashCode(), restaurante2.hashCode());
    }

    @Test
    void deveRetornarFalsoParaRestaurantesDiferentes() {
        Restaurante restaurante1 = new Restaurante(
                restauranteId, nome, tipoCozinha, horaAbertura, horaFechamento, endereco, proprietario
        );
        Restaurante restaurante2 = new Restaurante(
                UUID.randomUUID(), nome, tipoCozinha, horaAbertura, horaFechamento, endereco, proprietario
        );
        assertFalse(restaurante1.equals(restaurante2));
    }

    @Test
    void deveRetornarFalsoParaObjetoNuloOuClasseDiferente() {
        Restaurante restaurante = new Restaurante(
                restauranteId, nome, tipoCozinha, horaAbertura, horaFechamento, endereco, proprietario
        );
        assertFalse(restaurante.equals(null));
        assertFalse(restaurante.equals(new Object()));
    }

    @Test
    void deveRetornarVerdadeiroParaMesmoObjeto() {
        Restaurante restaurante = new Restaurante(
                restauranteId, nome, tipoCozinha, horaAbertura, horaFechamento, endereco, proprietario
        );
        assertTrue(restaurante.equals(restaurante));
    }
}

