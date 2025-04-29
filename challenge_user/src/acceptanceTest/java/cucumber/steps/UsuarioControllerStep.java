package cucumber.steps;

import br.com.fiap.tech.challenge_user.adapter.dto.request.UsuarioDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.request.UsuarioUpdateDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.response.UsuarioDtoResponse;
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
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioControllerStep {

    private static RequestSpecification requestSpecification;

    @Autowired
    private DataSource dataSource;

    @LocalServerPort
    int port;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Response response;

    private UsuarioDtoRequest usuarioDtoRequest;

    private UsuarioDtoResponse usuarioDtoResponse;

    private UsuarioUpdateDtoRequest usuarioUpdateDtoRequest;

    private UsuarioEntity usuarioEntity;

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

    @Dado("um UsuarioDtoRequest, com nome {string} e email {string} e login {string} e senha {string}")
    public void um_usuario_dto_request_com_nome_e_email_e_login_e_senha(
            String nome, String email, String login, String senha) {

        usuarioDtoRequest = new UsuarioDtoRequest(nome, email, login, senha, null);

        assertThat(usuarioDtoRequest).isNotNull();
    }

    @Quando("a requisição Post for feita no método create do UsuarioController")
    public void a_requisicao_post_for_feita_no_metodo_create_do_usuario_controller() {

        response = RestAssured
                .given().spec(requestSpecification)
                .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .body(usuarioDtoRequest)
                .when()
                .post();

        assertThat(response).isNotNull();
    }

    @Entao("receber ResponseEntity com HTTP {int} do UsuarioController")
    public void receber_response_entity_com_http_do_usuario_controller(Integer status) {

        assertEquals(status, response.getStatusCode());
    }

    @Entao("com UsuarioDtoResponse no body, com id e nome {string} e email {string} e login {string} e senha {string}")
    public void com_usuario_dto_response_no_body_com_id_e_nome_e_email_e_login_e_senha(
            String nome, String email, String login, String senha) {

        usuarioDtoResponse = response.as(UsuarioDtoResponse.class);

        assertThat(usuarioDtoResponse.usuarioId()).isNotNull();
        assertThat(usuarioDtoResponse.nome()).isEqualTo(nome);
        assertThat(usuarioDtoResponse.email()).isEqualTo(email);
        assertThat(usuarioDtoResponse.login()).isEqualTo(login);
        assertThat(usuarioDtoResponse.senha()).isEqualTo(senha);
    }

    @Entao("um Usuario salvo no database, com nome {string} e email {string} e login {string} e senha {string}")
    public void um_usuario_salvo_no_database_com_nome_e_email_e_login_e_senha(
            String nome, String email, String login, String senha) {

        var usuarioCreate = usuarioRepository.findById(usuarioDtoResponse.usuarioId()).get();

        assertThat(usuarioCreate.getNome()).isEqualTo(nome);
        assertThat(usuarioCreate.getEmail()).isEqualTo(email);
        assertThat(usuarioCreate.getLogin()).isEqualTo(login);
        assertThat(usuarioCreate.getSenha()).isEqualTo(senha);
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

    @Quando("uma requisição Delete for feita no método deleteById do UsuarioController")
    public void uma_requisicao_delete_for_feita_no_metodo_delete_by_id_do_usuario_controller() {

        response = RestAssured
                .given().spec(requestSpecification)
                    .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .when()
                    .delete("/" + usuarioEntity.getUsuarioId());

        assertThat(response).isNotNull();
    }

    @Entao("o Usuário foi apagado do banco de dados pelo UsuarioController")
    public void o_usuario_foi_apagado_do_banco_de_dados_pelo_usuario_controller() {

        var response = usuarioRepository.findById(usuarioEntity.getUsuarioId());

        assertThat(response).isEmpty();
    }

    @Dado("um UsuarioUpdateDtoRequest, com nome {string} e email {string} e login {string} e senha {string}")
    public void um_usuario_update_dto_request_com_nome_e_email_e_login_e_senha(
            String nome, String email, String login, String senha) {

        usuarioUpdateDtoRequest = new UsuarioUpdateDtoRequest(usuarioEntity.getUsuarioId(), nome, email, login, senha, null);

        assertThat(usuarioUpdateDtoRequest).isNotNull();
    }

    @Quando("uma requisição Put for feita no método update do UsuarioController")
    public void uma_requisicao_put_for_feita_no_metodo_update_do_usuario_controller() {

        response = RestAssured
                .given().spec(requestSpecification)
                    .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                    .body(usuarioUpdateDtoRequest)
                .when()
                    .put();

        assertThat(response).isNotNull();
    }

    @Entao("o Usuário no banco, possui nome {string} e email {string} e login {string} e senha {string}")
    public void o_usuario_no_banco_possui_nome_e_email_e_login_e_senha(
            String nome, String email, String login, String senha) {

        var usuarioAtualizado = usuarioRepository.findById(usuarioEntity.getUsuarioId()).get();

        assertThat(usuarioAtualizado.getUsuarioId()).isEqualTo(usuarioEntity.getUsuarioId());
        assertThat(usuarioAtualizado.getNome()).isEqualTo(nome);
        assertThat(usuarioAtualizado.getEmail()).isEqualTo(email);
        assertThat(usuarioAtualizado.getLogin()).isEqualTo(login);
        assertThat(usuarioAtualizado.getSenha()).isEqualTo(senha);
    }

    @Dado("um identificador ID de um usuário inexistente")
    public void um_identificador_id_de_um_usuario_inexistente() {

        usuarioEntity = UsuarioEntity.builder()
                .usuarioId(UUID.randomUUID())
                .build();

        assertThat(usuarioEntity.getUsuarioId()).isNotNull();
    }
}

