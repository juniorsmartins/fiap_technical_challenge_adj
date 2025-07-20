package br.com.fiap.tech.challenge_user.domain.models;

import br.com.fiap.tech.challenge_user.application.exception.http409.AtributoObrigatorioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemTest {

    private UUID itemId;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private boolean entrega;

    private String foto;

    @BeforeEach
    void setUp() {
        itemId = UUID.randomUUID();
        nome = "Pizza Margherita";
        descricao = "Pizza com molho de tomate, mussarela e manjericão";
        preco = new BigDecimal("45.90");
        entrega = true;
        foto = "pizza_margherita.jpg";
    }

    @Test
    void deveCriarItemComConstrutorVazio() {
        Item item = new Item();
        assertNull(item.getItemId());
        assertNull(item.getNome());
        assertNull(item.getDescricao());
        assertNull(item.getPreco());
        assertFalse(item.isEntrega()); // Valor padrão de boolean
        assertNull(item.getFoto());
    }

    @Test
    void deveCriarItemComConstrutorCincoParametros() {
        Item item = new Item(nome, descricao, preco, entrega, foto);
        assertNull(item.getItemId()); // itemId não é definido neste construtor
        assertEquals(nome, item.getNome());
        assertEquals(descricao, item.getDescricao());
        assertEquals(preco, item.getPreco());
        assertEquals(entrega, item.isEntrega());
        assertEquals(foto, item.getFoto());
    }

    @Test
    void deveCriarItemComConstrutorSeisParametros() {
        Item item = new Item(itemId, nome, descricao, preco, entrega, foto);
        assertEquals(itemId, item.getItemId());
        assertEquals(nome, item.getNome());
        assertEquals(descricao, item.getDescricao());
        assertEquals(preco, item.getPreco());
        assertEquals(entrega, item.isEntrega());
        assertEquals(foto, item.getFoto());
    }

    @Test
    void deveLancarExcecaoQuandoNomeEhNuloNoConstrutor() {
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> new Item(itemId, null, descricao, preco, entrega, foto)
        );
        assertEquals("nome", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoNomeEhEmBrancoNoConstrutor() {
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> new Item(itemId, "   ", descricao, preco, entrega, foto)
        );
        assertEquals("nome", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoDescricaoEhNulaNoConstrutor() {
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> new Item(itemId, nome, null, preco, entrega, foto)
        );
        assertEquals("descrição", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoDescricaoEhEmBrancoNoConstrutor() {
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> new Item(itemId, nome, "   ", preco, entrega, foto)
        );
        assertEquals("descrição", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoPrecoEhNuloNoConstrutor() {
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> new Item(itemId, nome, descricao, null, entrega, foto)
        );
        assertEquals("preço", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoFotoEhNulaNoConstrutor() {
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> new Item(itemId, nome, descricao, preco, entrega, null)
        );
        assertEquals("foto", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoFotoEhEmBrancoNoConstrutor() {
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> new Item(itemId, nome, descricao, preco, entrega, "   ")
        );
        assertEquals("foto", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoNomeEhNuloNoConstrutorCincoParametros() {
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> new Item(null, descricao, preco, entrega, foto)
        );
        assertEquals("nome", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoDescricaoEhNulaNoConstrutorCincoParametros() {
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> new Item(nome, null, preco, entrega, foto)
        );
        assertEquals("descrição", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoPrecoEhNuloNoConstrutorCincoParametros() {
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> new Item(nome, descricao, null, entrega, foto)
        );
        assertEquals("preço", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoFotoEhNulaNoConstrutorCincoParametros() {
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> new Item(nome, descricao, preco, entrega, null)
        );
        assertEquals("foto", exception.getValor());
    }

    @Test
    void deveDefinirNomeValido() {
        Item item = new Item();
        String novoNome = "Hambúrguer Artesanal";
        item.setNome(novoNome);
        assertEquals(novoNome, item.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoNomeEhNuloNoSetter() {
        Item item = new Item();
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> item.setNome(null)
        );
        assertEquals("nome", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoNomeEhEmBrancoNoSetter() {
        Item item = new Item();
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> item.setNome("   ")
        );
        assertEquals("nome", exception.getValor());
    }

    @Test
    void deveDefinirDescricaoValida() {
        Item item = new Item();
        String novaDescricao = "Hambúrguer com carne Angus e molho especial";
        item.setDescricao(novaDescricao);
        assertEquals(novaDescricao, item.getDescricao());
    }

    @Test
    void deveLancarExcecaoQuandoDescricaoEhNulaNoSetter() {
        Item item = new Item();
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> item.setDescricao(null)
        );
        assertEquals("descrição", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoDescricaoEhEmBrancoNoSetter() {
        Item item = new Item();
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> item.setDescricao("   ")
        );
        assertEquals("descrição", exception.getValor());
    }

    @Test
    void deveDefinirPrecoValido() {
        Item item = new Item();
        BigDecimal novoPreco = new BigDecimal("29.90");
        item.setPreco(novoPreco);
        assertEquals(novoPreco, item.getPreco());
    }

    @Test
    void deveLancarExcecaoQuandoPrecoEhNuloNoSetter() {
        Item item = new Item();
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> item.setPreco(null)
        );
        assertEquals("preço", exception.getValor());
    }

    @Test
    void deveDefinirEntregaValida() {
        Item item = new Item();
        item.setEntrega(false);
        assertFalse(item.isEntrega());
        item.setEntrega(true);
        assertTrue(item.isEntrega());
    }

    @Test
    void deveDefinirFotoValida() {
        Item item = new Item();
        String novaFoto = "hamburguer.jpg";
        item.setFoto(novaFoto);
        assertEquals(novaFoto, item.getFoto());
    }

    @Test
    void deveLancarExcecaoQuandoFotoEhNulaNoSetter() {
        Item item = new Item();
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> item.setFoto(null)
        );
        assertEquals("foto", exception.getValor());
    }

    @Test
    void deveLancarExcecaoQuandoFotoEhEmBrancoNoSetter() {
        Item item = new Item();
        AtributoObrigatorioException exception = assertThrows(
                AtributoObrigatorioException.class,
                () -> item.setFoto("   ")
        );
        assertEquals("foto", exception.getValor());
    }

    @Test
    void deveRetornarVerdadeiroParaItensIguais() {
        Item item1 = new Item(itemId, nome, descricao, preco, entrega, foto);
        Item item2 = new Item(itemId, "Outro Nome", "Outra Descrição", new BigDecimal("10.00"), false, "outra_foto.jpg");
        assertTrue(item1.equals(item2));
        assertEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    void deveRetornarFalsoParaItensDiferentes() {
        Item item1 = new Item(itemId, nome, descricao, preco, entrega, foto);
        Item item2 = new Item(UUID.randomUUID(), nome, descricao, preco, entrega, foto);
        assertFalse(item1.equals(item2));
    }

    @Test
    void deveRetornarFalsoParaObjetoNuloOuClasseDiferente() {
        Item item = new Item(itemId, nome, descricao, preco, entrega, foto);
        assertFalse(item.equals(null));
        assertFalse(item.equals(new Object()));
    }

    @Test
    void deveRetornarVerdadeiroParaMesmoObjeto() {
        Item item = new Item(itemId, nome, descricao, preco, entrega, foto);
        assertTrue(item.equals(item));
    }

    @Test
    void deveAceitarPrecoZeroNoConstrutor() {
        BigDecimal precoZero = BigDecimal.ZERO;
        Item item = new Item(itemId, nome, descricao, precoZero, entrega, foto);
        assertEquals(precoZero, item.getPreco());
    }
}

