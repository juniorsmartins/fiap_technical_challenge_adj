package cucumber.steps;

import br.com.fiap.tech.challenge_user.adapter.dto.request.ClienteDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.request.ClienteUpdateDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.request.EnderecoDtoRequest;
import br.com.fiap.tech.challenge_user.adapter.dto.response.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.dto.response.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.adapter.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.adapter.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.adapter.repository.ClienteRepository;
import br.com.fiap.tech.challenge_user.adapter.repository.EnderecoRepository;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClienteControllerStep {

    private static RequestSpecification requestSpecification;

    @Autowired
    private DataSource dataSource;

    @LocalServerPort
    int port;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    private Response response;

    private ClienteDtoRequest clienteDtoRequest;

    private ClienteDtoResponse clienteDtoResponse;

    private ClienteUpdateDtoRequest clienteUpdateDtoRequest;

    private ClienteEntity clienteEntity;

    private EnderecoDtoResponse enderecoDtoResponse;

    @Before
    public void setUp() {
        requestSpecification = new RequestSpecBuilder()
                .addHeader(ConstantsTest.HEADER_PARAM_ORIGIN, ConstantsTest.ORIGIN_FIAP)
                .setBasePath(ConstantsTest.PATH_CHALLENGE_CLIENTE)
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

    @Dado("cadastros de Clientes disponíveis no banco de dados")
    public void cadastros_de_clientes_disponiveis_no_banco_de_dados(io.cucumber.datatable.DataTable dataTable) {
        clienteRepository.deleteAll();

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

            var clienteEntidade = new ClienteEntity(
                    row.get("nome"),
                    row.get("email"),
                    row.get("login"),
                    row.get("senha"),
                    enderecoEntity,
                    row.get("numeroCartaoFidelidade"),
                    Date.from(Instant.now()),
                    null
            );

            clienteRepository.save(clienteEntidade);
        }
    }

    @Dado("um ClienteDtoRequest, com nome {string} e email {string} e login {string} e senha {string} e numeroCartaoFidelidade {string}")
    public void um_usuario_dto_request_com_nome_e_email_e_login_e_senha_e_numero_cartao_fidelidade(
            String nome, String email, String login, String senha, String numeroCartaoFidelidade) {

        clienteDtoRequest = new ClienteDtoRequest(nome, email, login, senha, null, numeroCartaoFidelidade);

        assertThat(clienteDtoRequest).isNotNull();
    }

    @Quando("a requisição Post for feita no método create do ClienteController")
    public void a_requisicao_post_for_feita_no_metodo_create_do_cliente_controller() {

        response = RestAssured
                .given().spec(requestSpecification)
                .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .body(clienteDtoRequest)
                .when()
                .post();

        assertThat(response).isNotNull();
    }

    @Entao("receber ResponseEntity com HTTP {int} do ClienteController")
    public void receber_response_entity_com_http_do_cliente_controller(Integer status) {
        assertEquals(status, response.getStatusCode());
    }

    @Entao("com ClienteDtoResponse no body, com id e nome {string} e email {string} e login {string} e senha {string} e numeroCartaoFidelidade {string}")
    public void com_usuario_dto_response_no_body_com_id_e_nome_e_email_e_login_e_senha_e_numero_cartao_fidelidade(
            String nome, String email, String login, String senha, String numeroCartaoFidelidade) {

        clienteDtoResponse = response.as(ClienteDtoResponse.class);

        assertThat(clienteDtoResponse.usuarioId()).isNotNull();
        assertThat(clienteDtoResponse.nome()).isEqualTo(nome);
        assertThat(clienteDtoResponse.email()).isEqualTo(email);
        assertThat(clienteDtoResponse.login()).isEqualTo(login);
        assertThat(clienteDtoResponse.senha()).isEqualTo(senha);
        assertThat(clienteDtoResponse.numeroCartaoFidelidade()).isEqualTo(numeroCartaoFidelidade);
    }

    @Entao("o Cliente cadastrado no banco de dados possui nome {string} e email {string} e login {string} e senha {string} e numeroCartaoFidelidade {string}")
    public void o_cliente_no_banco_possui_nome_e_email_e_login_e_senha_e_numero_cartao_fidelidade(
            String nome, String email, String login, String senha, String numeroCartaoFidelidade) {

        var usuario = clienteRepository.findById(clienteDtoResponse.usuarioId()).get();

        assertThat(usuario.getNome()).isEqualTo(nome);
        assertThat(usuario.getEmail()).isEqualTo(email);
        assertThat(usuario.getLogin()).isEqualTo(login);
        assertThat(usuario.getSenha()).isEqualTo(senha);
        assertThat(usuario.getNumeroCartaoFidelidade()).isEqualTo(numeroCartaoFidelidade);
    }

    @Entao("o Cliente no database possui nome {string} e email {string} e login {string} e senha {string} e numeroCartaoFidelidade {string}")
    public void o_cliente_no_database_possui_nome_e_email_e_login_e_senha_numero_cartao_fidelidade(
            String nome, String email, String login, String senha, String numeroCartaoFidelidade) {

        var cliente = clienteRepository.findById(clienteEntity.getUsuarioId()).get();

        assertThat(cliente.getNome()).isEqualTo(nome);
        assertThat(cliente.getEmail()).isEqualTo(email);
        assertThat(cliente.getLogin()).isEqualTo(login);
        assertThat(cliente.getSenha()).isEqualTo(senha);
        assertThat(cliente.getNumeroCartaoFidelidade()).isEqualTo(numeroCartaoFidelidade);
    }

    @Dado("um identificador ID de um cliente existente, com email {string}")
    public void um_identificador_id_de_um_cliente_existente_com_email(String email) {

        clienteEntity = clienteRepository.findByEmail(email).get();

        assertThat(clienteEntity).isNotNull();
    }

    @Quando("uma requisição Get for feita no método findById do ClienteController")
    public void uma_requisicao_get_for_feita_no_metodo_find_by_id_do_cliente_controller() {

        response = RestAssured
                .given().spec(requestSpecification)
                .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .when()
                .get("/" + clienteEntity.getUsuarioId());

        assertThat(response).isNotNull();
    }

    @Quando("uma requisição Delete for feita no método deleteById do ClienteController")
    public void uma_requisicao_delete_for_feita_no_metodo_delete_by_id_do_cliente_controller() {

        response = RestAssured
                .given().spec(requestSpecification)
                .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .when()
                .delete("/" + clienteEntity.getUsuarioId());

        assertThat(response).isNotNull();
    }

    @Entao("o Cliente foi apagado do banco de dados pelo ClienteController")
    public void o_cliente_foi_apagado_do_banco_de_dados_pelo_cliente_controller() {

        var response = clienteRepository.findById(clienteEntity.getUsuarioId());

        assertThat(response).isEmpty();
    }

    @Dado("um ClienteUpdateDtoRequest, com nome {string} e email {string} e login {string} e senha {string} e numeroCartaoFidelidade {string}")
    public void um_cliente_update_dto_request_com_nome_e_email_e_login_e_senha(
            String nome, String email, String login, String senha, String numeroCartaoFidelidade) {

        clienteUpdateDtoRequest = new ClienteUpdateDtoRequest(clienteEntity
                .getUsuarioId(), nome, email, login, senha, null, numeroCartaoFidelidade);

        assertThat(clienteUpdateDtoRequest).isNotNull();
    }

    @Quando("uma requisição Put for feita no método update do ClienteController")
    public void uma_requisicao_put_for_feita_no_metodo_update_do_cliente_controller() {

        response = RestAssured
                .given().spec(requestSpecification)
                .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .body(clienteUpdateDtoRequest)
                .when()
                .put();

        assertThat(response).isNotNull();
    }

    @Dado("um identificador ID de um cliente inexistente")
    public void um_identificador_id_de_um_cliente_inexistente() {

        clienteEntity = new ClienteEntity(
                UUID.randomUUID(),
                "nomeTeste",
                "emailTeste",
                "loginTeste",
                "senhaTeste",
                null,
                "1234-777-000",
                null,
                null
        );

        assertThat(clienteEntity.getUsuarioId()).isNotNull();
    }

    @Dado("um ClienteDtoRequest e EnderecoDtoRequest, com nome {string} e email {string} e login {string} e senha {string} e numeroCartaoFidelidade {string} e com cep {string} e logradouro {string} e número {string}")
    public void um_cliente_dto_request_e_endereco_dto_request_com_nome_e_email_e_login_e_senha_e_com_cep_e_logradouro_e_numero(
            String nome, String email, String login, String senha, String numeroCartaoFidelidade, String cep, String logradouro, String numero) {

        clienteDtoRequest = new ClienteDtoRequest(nome, email, login, senha,
                new EnderecoDtoRequest(cep, logradouro, numero),
                numeroCartaoFidelidade);

        assertThat(clienteDtoRequest).isNotNull();
        assertThat(clienteDtoRequest.endereco()).isNotNull();
    }

    @Entao("com EnderecoDtoResponse no body, com id e cep {string} e logradouro {string} e número {string}")
    public void com_endereco_dto_response_no_body_com_id_e_cep_e_logradouro_e_numero(
            String cep, String logradouro, String numero) {

        enderecoDtoResponse = clienteDtoResponse.endereco();

        assertThat(enderecoDtoResponse.enderecoId()).isNotNull();
        assertThat(enderecoDtoResponse.cep()).isEqualTo(cep);
        assertThat(enderecoDtoResponse.logradouro()).isEqualTo(logradouro);
        assertThat(enderecoDtoResponse.numero()).isEqualTo(numero);
    }

    @Entao("um Endereço salvo no database, com cep {string} e logradouro {string} e número {string}")
    public void um_endereco_salvo_no_database_com_cep_e_logradouro_e_numero(String cep, String logradouro, String numero) {

        var enderecoSalvo = enderecoRepository.findById(enderecoDtoResponse.enderecoId()).get();

        assertThat(enderecoSalvo.getCep()).isEqualTo(cep);
        assertThat(enderecoSalvo.getLogradouro()).isEqualTo(logradouro);
        assertThat(enderecoSalvo.getNumero()).isEqualTo(numero);
    }

    @Dado("um ClienteUpdateDtoRequest e EnderecoDtoRequest, com nome {string} e email {string} e login {string} e senha {string} e numeroCartaoFidelidade {string} e com cep {string} e logradouro {string} e número {string}")
    public void um_cliente_update_dto_request_e_endereco_dto_request_com_nome_e_email_e_login_e_senha_e_com_cep_e_logradouro_e_numero(
            String nome, String email, String login, String senha, String numeroCartaoFidelidade, String cep, String logradouro, String numero) {

        clienteUpdateDtoRequest = new ClienteUpdateDtoRequest(
                clienteEntity.getUsuarioId(),
                nome,
                email,
                login,
                senha,
                new EnderecoDtoRequest(cep, logradouro, numero),
                numeroCartaoFidelidade
        );

        assertThat(clienteUpdateDtoRequest).isNotNull();
        assertThat(clienteUpdateDtoRequest.endereco()).isNotNull();
    }

    @Entao("sem EnderecoDtoResponse no body")
    public void sem_endereco_dto_response_no_body() {

        clienteDtoResponse = response.as(ClienteDtoResponse.class);

        assertThat(clienteDtoResponse.usuarioId()).isNotNull();
        assertThat(clienteDtoResponse.endereco()).isNull();
    }

    @Entao("sem Endereço salvo no database")
    public void sem_endereco_salvo_no_database() {

        var usuarioAtualizado = clienteRepository.findById(clienteEntity.getUsuarioId()).get();

        assertThat(usuarioAtualizado.getUsuarioId()).isEqualTo(clienteEntity.getUsuarioId());
        assertThat(usuarioAtualizado.getEndereco()).isNull();
    }
}

