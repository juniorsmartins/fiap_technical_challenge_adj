package cucumber.steps;

import br.com.fiap.tech.challenge_user.infrastructure.dto.in.ItemDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.ItemDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.repository.ItemRepository;
import cucumber.config.ConstantsTest;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class ItemControllerStep {

    private static RequestSpecification requestSpecification;

    @Autowired
    private DataSource dataSource;

    @LocalServerPort
    int port;

    @Autowired
    private ItemRepository itemRepository;

    private ItemDtoRequest itemDtoRequest;

    private Response response;

    private ItemDtoResponse itemDtoResponse;

    @Before
    public void setUp() {
        requestSpecification = new RequestSpecBuilder()
                .addHeader(ConstantsTest.HEADER_PARAM_ORIGIN, ConstantsTest.ORIGIN_FIAP)
                .setBasePath(ConstantsTest.PATH_CHALLENGE_ITEM)
                .setPort(port)
                .build();
    }

    @Dado("ambiente de teste ativado para Item de Challenge_User")
    public void ambiente_de_teste_ativado_para_item_de_challenge_user() {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        var count = jdbcTemplate
                .queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES", Integer.class);
        assertThat(count).isNotNull();
    }

    @Dado("um ItemDtoRequest, como nome {string} e descricao {string} e preco {string} e entrega {string} e foto {string}")
    public void um_item_dto_request_como_nome_e_descricao_e_preco_e_entrega_e_foto(
            String nome, String descricao, String preco, String entrega, String foto) {

        itemDtoRequest = new ItemDtoRequest(
                nome, descricao, new BigDecimal(preco), Boolean.parseBoolean(entrega), foto
        );
    }

    @Quando("a requisição Post for feita no método create de ItemController")
    public void a_requisicao_post_for_feita_no_metodo_create_de_item_controller() {

        response = RestAssured
                .given().spec(requestSpecification)
                .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .body(itemDtoRequest)
                .when()
                .post();

        assertThat(response).isNotNull();
    }

    @Entao("receber ResponseEntity com HTTP {int} do ItemController")
    public void receber_response_entity_com_http_do_item_controller(Integer status) {

        assertEquals(status, response.getStatusCode());
    }

    @Entao("com ItemDtoResponse no body, com id e nome {string} e descricao {string} e preco {string} e entrega {string} e foto {string}")
    public void com_item_dto_response_no_body_com_id_e_nome_e_descricao_e_preco_e_entrega_e_foto(
            String nome, String descricao, String preco, String entrega, String foto) {

        itemDtoResponse = response.as(ItemDtoResponse.class);

        assertThat(itemDtoResponse.itemId()).isNotNull();
        assertThat(itemDtoResponse.nome()).isEqualTo(nome);
        assertThat(itemDtoResponse.descricao()).isEqualTo(descricao);
        assertEquals(0, new BigDecimal(preco).compareTo(itemDtoResponse.preco()));
        assertThat(itemDtoResponse.entrega()).isEqualTo(Boolean.parseBoolean(entrega));
        assertThat(itemDtoResponse.foto()).isEqualTo(foto);
    }

    @Entao("com Item salvo no database, com nome {string} e descricao {string} e preco {string} e entrega {string} e foto {string}")
    public void com_item_salvo_no_database_com_nome_e_descricao_e_preco_e_entrega_e_foto(
            String nome, String descricao, String preco, String entrega, String foto) {

        var entidade = itemRepository.findById(itemDtoResponse.itemId()).get();

        assertThat(entidade.getNome()).isEqualTo(nome);
        assertThat(entidade.getDescricao()).isEqualTo(descricao);
        assertEquals(0, new BigDecimal(preco).compareTo(entidade.getPreco()));
        assertThat(entidade.isEntrega()).isEqualTo(Boolean.parseBoolean(entrega));
        assertThat(entidade.getFoto()).isEqualTo(foto);
    }
}

