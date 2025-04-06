package cucumber.steps;

import br.com.fiap.tech.challenge_user.adapter.dto.UsuarioDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.entity.UsuarioEntity;
import br.com.fiap.tech.challenge_user.adapter.repository.UsuarioRepository;
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

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioControllerGetStep {

    private static RequestSpecification requestSpecification;

    @Autowired
    private DataSource dataSource;

    @LocalServerPort
    int port;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Response response;

    private UsuarioDtoResponse usuarioDtoResponse;

    private UsuarioEntity usuarioEntity;

    @Before
    public void setUp() {
        requestSpecification = new RequestSpecBuilder()
                .addHeader(ConstantsTest.HEADER_PARAM_ORIGIN, ConstantsTest.ORIGIN_FIAP)
                .setBasePath(ConstantsTest.PATH_CHALLENGE_USER)
                .setPort(port)
                .build();
    }

    @Dado("cadastros de Usuarios disponíveis no banco de dados")
    public void cadastros_de_usuarios_disponiveis_no_banco_de_dados(io.cucumber.datatable.DataTable dataTable) {
        usuarioRepository.deleteAll();

        List<Map<String, String>> usuariosData = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : usuariosData) {
            var usuarioEntity = UsuarioEntity.builder()
                    .nome(row.get("nome"))
                    .email(row.get("email"))
                    .login(row.get("login"))
                    .senha(row.get("senha"))
                    .build();

            usuarioRepository.save(usuarioEntity);
        }
    }

    @Dado("um identificador ID de um usuário existente, com email {string}")
    public void um_identificador_id_de_um_usuario_existente_com_email(String email) {
        usuarioEntity = usuarioRepository.findByEmail(email).get();
        assertThat(usuarioEntity).isNotNull();
    }

    @Quando("uma requisição Get for feita no método findById do UsuarioController")
    public void uma_requisicao_get_for_feita_no_metodo_find_by_id_do_usuario_controller() {
        response = RestAssured
                .given().spec(requestSpecification)
                    .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .when()
                    .get("/" + usuarioEntity.getUsuarioId());

        assertThat(response).isNotNull();
    }

    @Entao("receber ResponseEntity com HTTP {int} do Get do UsuarioController")
    public void receber_response_entity_com_http_do_get_do_usuario_controller(Integer status) {
        assertEquals(status, response.getStatusCode());
    }

    @Entao("com UsuarioDtoResponse no body, com id e nome {string} e email {string} e login {string}")
    public void com_usuario_dto_response_no_body_com_id_e_nome_e_email_e_login(String nome, String email, String login) {

        usuarioDtoResponse = response.as(UsuarioDtoResponse.class);

        assertThat(usuarioDtoResponse).isNotNull();
        assertThat(usuarioDtoResponse.usuarioId()).isEqualTo(usuarioEntity.getUsuarioId());
        assertThat(usuarioDtoResponse.nome()).isEqualTo(nome);
        assertThat(usuarioDtoResponse.email()).isEqualTo(email);
        assertThat(usuarioDtoResponse.login()).isEqualTo(login);
    }
}

