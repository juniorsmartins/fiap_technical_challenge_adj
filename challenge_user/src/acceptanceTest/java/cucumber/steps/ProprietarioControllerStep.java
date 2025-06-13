package cucumber.steps;

import br.com.fiap.tech.challenge_user.infrastructure.dto.in.EnderecoDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.ProprietarioDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.SenhaDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.infrastructure.repository.EnderecoRepository;
import br.com.fiap.tech.challenge_user.infrastructure.repository.ProprietarioRepository;
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
import java.util.*;

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

    @Autowired
    private EnderecoRepository enderecoRepository;

    private Response response;

    private ProprietarioDtoRequest proprietarioDtoRequest;

    private ProprietarioDtoResponse proprietarioDtoResponse;

    private EnderecoDtoResponse enderecoDtoResponse;

    private ProprietarioEntity proprietarioEntity;

    private SenhaDtoRequest senhaDtoRequest;

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

    @Dado("um ProprietarioDtoRequest e EnderecoDtoRequest, com nome {string} e email {string} e login {string} e senha {string} e descricao {string} e com cep {string} e logradouro {string} e número {string}")
    public void um_proprietario_dto_request_e_endereco_dto_request_com_nome_e_email_e_login_e_senha_e_descricao_e_com_cep_e_logradouro_e_nuúmero(
            String nome, String email, String login, String senha, String descricao, String cep, String logradouro, String numero) {

        proprietarioDtoRequest = new ProprietarioDtoRequest(
                nome, email, login, senha,
                new EnderecoDtoRequest(cep, logradouro, numero),
                descricao
        );

        assertThat(proprietarioDtoRequest).isNotNull();
        assertThat(proprietarioDtoRequest.endereco()).isNotNull();
    }

    @Entao("com EnderecoDtoResponse no body, com id e cep {string} e logradouro {string} e número {string}, pelo ProprietarioController")
    public void com_endereco_dto_response_no_body_com_id_e_cep_e_logradouro_e_número_pelo_proprietario_controller(
            String cep, String logradouro, String numero) {

        enderecoDtoResponse = proprietarioDtoResponse.endereco();

        assertThat(enderecoDtoResponse.enderecoId()).isNotNull();
        assertThat(enderecoDtoResponse.cep()).isEqualTo(cep);
        assertThat(enderecoDtoResponse.logradouro()).isEqualTo(logradouro);
        assertThat(enderecoDtoResponse.numero()).isEqualTo(numero);
    }

    @Entao("um Endereço salvo no database, com cep {string} e logradouro {string} e número {string}, pelo ProprietarioController")
    public void um_endereco_salvo_no_database_com_cep_e_logradouro_e_numero_pelo_proprietario_controller(
            String cep, String logradouro, String numero) {

        var enderecoSalvo = enderecoRepository.findById(enderecoDtoResponse.enderecoId()).get();

        assertThat(enderecoSalvo.getCep()).isEqualTo(cep);
        assertThat(enderecoSalvo.getLogradouro()).isEqualTo(logradouro);
        assertThat(enderecoSalvo.getNumero()).isEqualTo(numero);
    }

    @Dado("um identificador ID de um proprietario existente, com email {string}")
    public void um_identificador_id_de_um_proprietario_existente_com_email(String email) {

        proprietarioEntity = proprietarioRepository.findByEmail(email).get();
        assertThat(proprietarioEntity).isNotNull();
    }

    @Quando("uma requisição Get for feita no método findById do ProprietarioController")
    public void uma_requisicao_get_for_feita_no_metodo_find_by_id_do_proprietario_controller() {

        response = RestAssured
                .given().spec(requestSpecification)
                .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .when()
                .get("/" + proprietarioEntity.getUsuarioId());

        assertThat(response).isNotNull();
    }

    @Dado("um identificador ID de um proprietario inexistente")
    public void um_identificador_id_de_um_proprietario_inexistente() {

        proprietarioEntity = new ProprietarioEntity(
                UUID.randomUUID(),
                "nomeTeste",
                "emailTeste",
                "loginTeste",
                "senhaTeste",
                null,
                "Advogado",
                null,
                null
        );

        assertThat(proprietarioEntity.getUsuarioId()).isNotNull();
    }

    @Quando("uma requisição Delete for feita no método deleteById do ProprietarioController")
    public void uma_requisicao_delete_for_feita_no_metodo_delete_by_id_do_proprietario_controller() {

        response = RestAssured
                .given().spec(requestSpecification)
                .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .when()
                .delete("/" + proprietarioEntity.getUsuarioId());

        assertThat(response).isNotNull();
    }

    @Entao("o Proprietario foi apagado do banco de dados pelo ProprietarioController")
    public void o_proprietario_foi_apagado_do_banco_de_dados_pelo_proprietario_controller() {

        var response = proprietarioRepository.findById(proprietarioEntity.getUsuarioId());

        assertThat(response).isEmpty();
    }

    @Quando("uma requisição Put for feita no método update do ProprietarioController")
    public void uma_requisicao_put_for_feita_no_metodo_update_do_proprietario_controller() {

        response = RestAssured
                .given().spec(requestSpecification)
                .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .body(proprietarioDtoRequest)
                .when()
                .put("/" + proprietarioEntity.getUsuarioId());

        assertThat(response).isNotNull();
    }

    @Entao("o Proprietario no database possui nome {string} e email {string} e login {string} e senha {string} e descricao {string}")
    public void o_proprietario_no_database_possui_nome_e_email_e_login_e_senha_e_descricao(
            String nome, String email, String login, String senha, String descricao) {

        var proprietario = proprietarioRepository.findById(proprietarioEntity.getUsuarioId()).get();

        assertThat(proprietario.getNome()).isEqualTo(nome);
        assertThat(proprietario.getEmail()).isEqualTo(email);
        assertThat(proprietario.getLogin()).isEqualTo(login);
        assertThat(proprietario.getSenha()).isEqualTo(senha);
        assertThat(proprietario.getDescricao()).isEqualTo(descricao);
    }

    @Entao("sem EnderecoDtoResponse no body pelo ProprietarioController")
    public void sem_endereco_dto_response_no_body_pelo_proprietario_controller() {

        proprietarioDtoResponse = response.as(ProprietarioDtoResponse.class);
        assertThat(proprietarioDtoResponse.usuarioId()).isNotNull();
        assertThat(proprietarioDtoResponse.endereco()).isNull();
    }

    @Entao("sem Endereço salvo no database pelo ProprietarioController")
    public void sem_endereco_salvo_no_database_pelo_proprietario_controller() {

        var proprietarioAtualizado = proprietarioRepository
                .findById(proprietarioEntity.getUsuarioId()).get();
        assertThat(proprietarioAtualizado.getUsuarioId()).isEqualTo(proprietarioEntity.getUsuarioId());
        assertThat(proprietarioAtualizado.getEndereco()).isNull();
    }

    @Quando("uma requisição Get for feita, com nome {string} no filtro, no método search do ProprietarioController")
    public void uma_requisicao_get_for_feita_com_nome_no_filtro_no_metodo_search_do_proprietario_controller(
            String nomes) {

        response = RestAssured
                .given().spec(requestSpecification)
                .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .queryParam("nome", nomes)
                .when()
                .get();

        assertThat(response).isNotNull();
    }

    @Entao("a resposta deve conter apenas proprietario, com nome {string}, no método search do ProprietarioController")
    public void a_resposta_deve_conter_apenas_proprietario_com_nome_no_metodo_search_do_proprietario_controller(
            String nomes) {

        var nomesEsperados = Arrays.asList(nomes.trim().split(","));

        List<ProprietarioDtoResponse> content = response.jsonPath()
                .getList("content", ProprietarioDtoResponse.class);

        assertThat(content).isNotEmpty();
        assertThat(content)
                .allMatch(dto -> nomesEsperados.contains(dto.nome()))
                .extracting(ProprietarioDtoResponse::nome)
                .containsOnlyOnceElementsOf(nomesEsperados);
    }

    @Quando("uma requisição Get for feita, com email {string} no filtro, no método search do ProprietarioController")
    public void uma_requisicao_get_for_feita_com_email_no_filtro_no_metodo_search_do_proprietario_controller(String emails) {

        response = RestAssured
                .given().spec(requestSpecification)
                .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .queryParam("email", emails)
                .when()
                .get();

        assertThat(response).isNotNull();
    }

    @Entao("a resposta deve conter apenas proprietarios, com email {string}, no método search do ProprietarioController")
    public void a_resposta_deve_conter_apenas_proprietarios_com_email_no_metodo_search_do_proprietario_controller(String emails) {

        var valores = Arrays.asList(emails.trim().split(","));

        List<ProprietarioDtoResponse> content = response.jsonPath()
                .getList("content", ProprietarioDtoResponse.class);

        assertThat(content).isNotEmpty();
        assertThat(content)
                .allMatch(dto ->valores.contains(dto.email()))
                .extracting(ProprietarioDtoResponse::email)
                .containsOnlyOnceElementsOf(valores);
    }

    @Quando("uma requisição Get for feita, com descricao {string} no filtro, no método search do ProprietarioController")
    public void uma_requisicao_get_for_feita_com_descricao_no_filtro_no_metodo_search_do_proprietario_controller(String descricoes) {

        response = RestAssured
                .given().spec(requestSpecification)
                .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .queryParam("descricao", descricoes)
                .when()
                .get();

        assertThat(response).isNotNull();
    }

    @Entao("a resposta deve conter apenas proprietarios, com descricao {string}, no método search do ProprietarioController")
    public void a_resposta_deve_conter_apenas_proprietarios_com_descricao_no_metodo_search_do_proprietario_controller(String descricoes) {

        var valores = Arrays.asList(descricoes.trim().split(","));

        List<ProprietarioDtoResponse> content = response.jsonPath()
                .getList("content", ProprietarioDtoResponse.class);

        assertThat(content).isNotEmpty();
        assertThat(content)
                .allMatch(dto ->valores.contains(dto.descricao()))
                .extracting(ProprietarioDtoResponse::descricao)
                .containsOnlyOnceElementsOf(valores);
    }

    @Dado("um SenhaDtoRequest, com senhaAntiga {string} e senhaNova {string}, para o ProprietarioController")
    public void um_senha_dto_request_com_senha_antiga_e_senha_nova_para_o_proprietario_controller(
            String senhaAntiga, String senhaNova) {

        senhaDtoRequest = new SenhaDtoRequest(
                proprietarioEntity.getUsuarioId(), senhaAntiga, senhaNova);

        assertThat(senhaDtoRequest).isNotNull();
    }

    @Quando("uma requisição Patch for feita no método updatePassword do ProprietarioController")
    public void uma_requisicao_patch_for_feita_no_metodo_update_password_do_proprietario_controller() {

        response = RestAssured
                .given().spec(requestSpecification)
                .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .body(senhaDtoRequest)
                .when()
                .patch();

        assertThat(response).isNotNull();
    }
}

