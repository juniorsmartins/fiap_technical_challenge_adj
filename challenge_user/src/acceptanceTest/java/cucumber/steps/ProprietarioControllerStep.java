package cucumber.steps;

import br.com.fiap.tech.challenge_user.adapter.dto.request.ProprietarioDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.response.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.adapter.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.adapter.repository.ProprietarioRepository;
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
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class ProprietarioControllerStep {

    private static RequestSpecification requestSpecification;

    @Autowired
    private DataSource dataSource;

    @LocalServerPort
    int port;

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    private Response response;

    private ProprietarioDtoRequest proprietarioDtoRequest;

    private ProprietarioDtoResponse proprietarioDtoResponse;

    @Before
    public void setUp() {
        requestSpecification = new RequestSpecBuilder()
                .addHeader(ConstantsTest.HEADER_PARAM_ORIGIN, ConstantsTest.ORIGIN_FIAP)
                .setBasePath(ConstantsTest.PATH_CHALLENGE_PROPRIETARIO)
                .setPort(port)
                .build();
    }

    @Dado("ambiente de teste ativado para Proprietário de Challenge_User")
    public void ambiente_de_teste_ativado_para_proprietário_de_challenge_user() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        var count = jdbcTemplate
                .queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES", Integer.class);
        assertThat(count).isNotNull();
    }

    @Dado("cadastros de Proprietários disponíveis no banco de dados")
    public void cadastros_de_proprietarios_disponiveis_no_banco_de_dados(io.cucumber.datatable.DataTable dataTable) {
        proprietarioRepository.deleteAll();

        List<Map<String, String>> usuariosData = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : usuariosData) {
            EnderecoEntity enderecoEntity = null;

            if (!row.get("cep").isEmpty()) {
                enderecoEntity = EnderecoEntity.builder()
                        .cep(row.get("cep"))
                        .logradouro(row.get("logradouro"))
                        .numero(row.get("numero"))
                        .build();
            }

            var proprietarioEntidade = new ProprietarioEntity(
                    row.get("nome"),
                    row.get("email"),
                    row.get("login"),
                    row.get("senha"),
                    enderecoEntity,
                    row.get("descricao"),
                    Date.from(Instant.now()),
                    null
            );

            proprietarioRepository.save(proprietarioEntidade);
        }
    }

    @Dado("um ProprietarioDtoRequest, com nome {string} e email {string} e login {string} e senha {string} e descricao {string}")
    public void um_proprietario_dto_request_com_nome_e_email_e_login_e_senha_e_descricao(
            String nome, String email, String login, String senha, String descricao) {

        proprietarioDtoRequest = new ProprietarioDtoRequest(nome, email, login, senha, null, descricao);
        assertThat(proprietarioDtoRequest).isNotNull();
    }

    @Quando("a requisição Post for feita no método create do ProprietarioController")
    public void a_requisicao_post_for_feita_no_metodo_create_do_proprietario_controller() {

        response = RestAssured
                .given().spec(requestSpecification)
                .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .body(proprietarioDtoRequest)
                .when()
                .post();

        assertThat(response).isNotNull();
    }

    @Entao("receber ResponseEntity com HTTP {int} do ProprietarioController")
    public void receber_response_entity_com_http_do_proprietario_controller(Integer status) {
        assertEquals(status, response.getStatusCode());
    }

    @Entao("com ProprietarioDtoResponse no body, com id e nome {string} e email {string} e login {string} e senha {string} e descricao {string}")
    public void com_proprietario_dto_response_no_body_com_id_e_nome_e_email_e_login_e_senha_e_descricao(
            String nome, String email, String login, String senha, String descricao) {

        proprietarioDtoResponse = response.as(ProprietarioDtoResponse.class);

        assertThat(proprietarioDtoResponse.usuarioId()).isNotNull();
        assertThat(proprietarioDtoResponse.nome()).isEqualTo(nome);
        assertThat(proprietarioDtoResponse.email()).isEqualTo(email);
        assertThat(proprietarioDtoResponse.login()).isEqualTo(login);
        assertThat(proprietarioDtoResponse.senha()).isEqualTo(senha);
        assertThat(proprietarioDtoResponse.descricao()).isEqualTo(descricao);
    }

    @Entao("o Proprietario cadastrado no banco de dados possui nome {string} e email {string} e login {string} e senha {string} e descricao {string}")
    public void o_proprietario_cadastrado_no_banco_de_dados_possui_nome_e_email_e_login_e_senha_e_descricao(
            String nome, String email, String login, String senha, String descricao) {

        var proprietario = proprietarioRepository
                .findById(proprietarioDtoResponse.usuarioId()).get();

        assertThat(proprietario.getNome()).isEqualTo(nome);
        assertThat(proprietario.getEmail()).isEqualTo(email);
        assertThat(proprietario.getLogin()).isEqualTo(login);
        assertThat(proprietario.getSenha()).isEqualTo(senha);
        assertThat(proprietario.getDescricao()).isEqualTo(descricao);
    }
}

