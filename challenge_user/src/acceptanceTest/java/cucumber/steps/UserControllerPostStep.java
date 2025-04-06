package cucumber.steps;

import br.com.fiap.tech.challenge_user.adapter.dto.UsuarioDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.UsuarioDtoResponse;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerPostStep {

    private static RequestSpecification requestSpecification;

    @Autowired
    private DataSource dataSource;

    @LocalServerPort // Esta anotação injeta a porta selecionada pelo Spring Boot
    int port;

    private Response response;

    private UsuarioDtoRequest usuarioDtoRequest;

    @Before
    public void setUp() {
        requestSpecification = new RequestSpecBuilder()
                .addHeader(ConstantsTest.HEADER_PARAM_ORIGIN, ConstantsTest.ORIGIN_FIAP)
                .setBasePath(ConstantsTest.PATH_CHALLENGE_USER)
                .setPort(port)
                .build();
    }

    @Dado("ambiente de teste ativado para Challenge_User")
    public void ambiente_de_teste_ativado_para_challenge_user() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        var count = jdbcTemplate
                .queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES", Integer.class);
        assertThat(count).isNotNull();
    }

    @Dado("um UserDtoRequest válido, com nome {string} e email {string} e login {string} e senha {string}")
    public void um_user_dto_request_valido_com_nome_e_email_e_login_e_senha(
            String nome, String email, String login, String senha) {
        usuarioDtoRequest = new UsuarioDtoRequest(nome, email, login, senha);
        assertThat(usuarioDtoRequest).isNotNull();
    }

    @Quando("a requisição Post for feita no método create do UserController")
    public void a_requisicao_post_for_feita_no_metodo_create_do_user_controller() {
        response = RestAssured
                .given().spec(requestSpecification)
                    .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                    .body(usuarioDtoRequest)
                .when()
                    .post();
    }

    @Entao("receber ResponseEntity com HTTP {int} do UserController")
    public void receber_response_entity_com_http_do_user_controller(Integer status) {
        assertEquals(status, response.getStatusCode());
    }

    @Entao("com UserDtoRequest no body, com nome {string} e email {string} e login {string} e senha {string}")
    public void com_user_dto_request_no_body_com_nome_e_email_e_login_e_senha(
            String nome, String email, String login, String senha) {

        var body = response.as(UsuarioDtoResponse.class);

        assertThat(body).isNotNull();
        assertThat(body.usuarioId()).isNotNull();
        assertThat(body.nome()).isEqualTo(nome);
        assertThat(body.email()).isEqualTo(email);
        assertThat(body.login()).isEqualTo(login);
        assertThat(body.senha()).isEqualTo(senha);
    }
}

