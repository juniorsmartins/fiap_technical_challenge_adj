package cucumber.steps;

import br.com.fiap.tech.challenge_user.application.mapper.ProprietarioMapper;
import br.com.fiap.tech.challenge_user.domain.model.enums.TipoCozinhaEnum;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.EnderecoDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.RestauranteDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.RestauranteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.infrastructure.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.infrastructure.entity.RestauranteEntity;
import br.com.fiap.tech.challenge_user.infrastructure.repository.*;
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

public final class RestauranteControllerStep {

    private static RequestSpecification requestSpecification;

    @Autowired
    private DataSource dataSource;

    @LocalServerPort
    int port;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private RestauranteDtoRequest restauranteDtoRequest;

    private Response response;

    private RestauranteDtoResponse restauranteDtoResponse;

    private RestauranteEntity restauranteEntity;

    private EnderecoEntity enderecoEntity;

    private EnderecoDtoResponse enderecoDtoResponse;

    private ProprietarioMapper proprietarioMapper;

    @Before
    public void setUp() {
        requestSpecification = new RequestSpecBuilder()
                .addHeader(ConstantsTest.HEADER_PARAM_ORIGIN, ConstantsTest.ORIGIN_FIAP)
                .setBasePath(ConstantsTest.PATH_CHALLENGE_RESTAURANTE)
                .setPort(port)
                .build();
    }

    @Dado("ambiente de teste ativado para Restaurante de Challenge_User")
    public void ambiente_de_teste_ativado_para_restaurante_de_challenge_user() {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        var count = jdbcTemplate
                .queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES", Integer.class);
        assertThat(count).isNotNull();
    }

    @Dado("cadastros de Clientes disponíveis no banco de dados para RestauranteController")
    public void cadastros_de_clientes_disponiveis_no_banco_de_dados_para_restaurante_controller(io.cucumber.datatable.DataTable dataTable) {
        clienteRepository.deleteAll();

        List<Map<String, String>> usuariosData = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : usuariosData) {

            var clienteEntidade = new ClienteEntity(
                    row.get("nome"),
                    row.get("email"),
                    row.get("login"),
                    row.get("senha"),
                    null,
                    row.get("numeroCartaoFidelidade"),
                    Date.from(Instant.now()),
                    null
            );

            clienteRepository.save(clienteEntidade);
        }
    }

    @Dado("cadastros de Proprietários disponíveis no banco de dados para RestauranteController")
    public void cadastros_de_proprietarios_disponiveis_no_banco_de_dados_para_restaurante_controller(io.cucumber.datatable.DataTable dataTable) {
        restauranteRepository.deleteAll();
        proprietarioRepository.deleteAll();

        List<Map<String, String>> usuariosData = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : usuariosData) {

            var entidade = new ProprietarioEntity(
                    row.get("nome"),
                    row.get("email"),
                    row.get("login"),
                    row.get("senha"),
                    null,
                    row.get("numeroCartaoFidelidade"),
                    Date.from(Instant.now()),
                    null
            );

            proprietarioRepository.saveAndFlush(entidade);
        }
    }

    @Dado("cadastros de Restaurantes disponíveis no banco de dados")
    public void cadastros_de_restaurantes_disponiveis_no_banco_de_dados(io.cucumber.datatable.DataTable dataTable) {
        restauranteRepository.deleteAll();

        List<Map<String, String>> massaDados = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : massaDados) {

            enderecoEntity = new EnderecoEntity();
            enderecoEntity.setCep(row.get("cep"));
            enderecoEntity.setLogradouro(row.get("logradouro"));
            enderecoEntity.setNumero(row.get("numero"));

            var proprietarioEntity = proprietarioRepository.findByEmail(row.get("proprietario")).get();

            var entidade = new RestauranteEntity();
            entidade.setNome(row.get("nome"));
            entidade.setTipoCozinhaEnum(TipoCozinhaEnum.valueOf(row.get("tipoCozinhaEnum")));
            entidade.setEndereco(enderecoEntity);
            entidade.setProprietario(proprietarioEntity);

            restauranteRepository.save(entidade);
        }
    }

    @Dado("um RestauranteDtoRequest, com nome {string} e tipoCozinhaEnum {string}, e EnderecoDtoRequest, com cep {string} e logradouro {string} e número {string},e Proprietario, com email {string}")
    public void um_restaurante_dto_request_com_nome_e_endereco_dto_request_com_cep_e_logradouro_e_numero_e_proprietario_com_email(
            String nome, String tipoCozinhaEnum, String cep, String logradouro, String numero, String email) {

//        var proprietario = proprietarioRepository.findByEmail(email).get();
        var proprietario = usuarioRepository.findByEmail(email).get();

        restauranteDtoRequest = new RestauranteDtoRequest(
                nome, TipoCozinhaEnum.valueOf(tipoCozinhaEnum),
                new EnderecoDtoRequest(cep, logradouro, numero), proprietario.getUsuarioId()
        );

        assertThat(restauranteDtoRequest).isNotNull();
        assertThat(restauranteDtoRequest.endereco()).isNotNull();
        assertThat(restauranteDtoRequest.proprietario()).isNotNull();
    }

    @Quando("a requisição Post for feita no método create do RestauranteController")
    public void a_requisicao_post_for_feita_no_metodo_create_do_restaurante_controller() {

        response = RestAssured
                .given().spec(requestSpecification)
                .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .body(restauranteDtoRequest)
                .when()
                .post();

        assertThat(response).isNotNull();
    }

    @Entao("receber ResponseEntity com HTTP {int} do RestauranteController")
    public void receber_response_entity_com_http_do_restaurante_controller(Integer status) {

        assertEquals(status, response.getStatusCode());
    }

    @Entao("com RestauranteDtoResponse no body, com id e nome {string} e tipoCozinhaEnum {string}")
    public void com_restaurante_dto_response_no_body_com_id_e_nome(String nome, String tipoCozinhaEnum) {

        restauranteDtoResponse = response.as(RestauranteDtoResponse.class);

        assertThat(restauranteDtoResponse.restauranteId()).isNotNull();
        assertThat(restauranteDtoResponse.nome()).isEqualTo(nome);
        assertThat(restauranteDtoResponse.tipoCozinhaEnum()).isEqualTo(TipoCozinhaEnum.valueOf(tipoCozinhaEnum));
    }

    @Entao("com EnderecoDtoResponse no body, com id e cep {string} e logradouro {string} e número {string}, pelo RestauranteController")
    public void com_endereco_dto_response_no_body_com_id_e_cep_e_logradouro_e_numero_pelo_restaurante_controller(
            String cep, String logradouro, String numero) {

        enderecoDtoResponse = restauranteDtoResponse.endereco();

        assertThat(enderecoDtoResponse.enderecoId()).isNotNull();
        assertThat(enderecoDtoResponse.cep()).isEqualTo(cep);
        assertThat(enderecoDtoResponse.logradouro()).isEqualTo(logradouro);
        assertThat(enderecoDtoResponse.numero()).isEqualTo(numero);
    }

    @Entao("com Proprietario no body, com email {string}")
    public void com_proprietario_no_body_com_email(String email) {

        var proprietario = restauranteDtoResponse.proprietario();

        assertThat(proprietario).isNotNull();
        assertThat(proprietario.usuarioId()).isNotNull();
        assertThat(proprietario.email()).isEqualTo(email);
    }

    @Entao("o Restaurante cadastrado no banco de dados possui nome {string} e tipoCozinhaEnum {string}")
    public void o_restaurante_cadastrado_no_banco_de_dados_possui_nome(String nome, String tipoCozinhaEnum) {

        var entidade = restauranteRepository.findById(restauranteDtoResponse.restauranteId()).get();

        assertThat(entidade.getNome()).isEqualTo(nome);
        assertThat(entidade.getTipoCozinhaEnum()).isEqualTo(TipoCozinhaEnum.valueOf(tipoCozinhaEnum));
    }
    
    @Entao("um Endereço salvo no database, com cep {string} e logradouro {string} e número {string}, pelo RestauranteController")
    public void um_endereco_salvo_no_database_com_cep_e_logradouro_e_numero_pelo_restaurante_controller(
            String cep, String logradouro, String numero) {

        var enderecoSalvo = enderecoRepository.findById(enderecoDtoResponse.enderecoId()).get();

        assertThat(enderecoSalvo.getCep()).isEqualTo(cep);
        assertThat(enderecoSalvo.getLogradouro()).isEqualTo(logradouro);
        assertThat(enderecoSalvo.getNumero()).isEqualTo(numero);
    }

    @Dado("um identificador ID de um Restaurante existente, com nome {string}")
    public void um_identificador_id_de_um_restaurante_existente_com_nome(String nome) {

        restauranteEntity = restauranteRepository.findByNome(nome).get();

        assertThat(restauranteEntity).isNotNull();
    }

    @Quando("uma requisição Get for feita no método findById do RestauranteController")
    public void uma_requisicao_get_for_feita_no_metodo_find_by_id_do_restaurante_controller() {

        response = RestAssured
                .given().spec(requestSpecification)
                .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .when()
                .get("/" + restauranteEntity.getRestauranteId());

        assertThat(response).isNotNull();
    }

    @Dado("um identificador ID de um Restaurante inexistente")
    public void um_identificador_id_de_um_restaurante_inexistente() {

        var proprietario = proprietarioRepository.findByEmail("galilei@yahoo.com").get();

        restauranteEntity = new RestauranteEntity(UUID.randomUUID(), "nomeTeste", TipoCozinhaEnum.ARABE,
                new EnderecoEntity(UUID.randomUUID(), "78000-000", "Rua Teste", "300"),
                proprietario);

        assertThat(restauranteEntity.getRestauranteId()).isNotNull();
    }

    @Quando("uma requisição Delete for feita no método deleteById do RestauranteController")
    public void uma_requisicao_delete_for_feita_no_metodo_delete_by_id_do_restaurante_controller() {

        response = RestAssured
                .given().spec(requestSpecification)
                .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .when()
                .delete("/" + restauranteEntity.getRestauranteId());

        assertThat(response).isNotNull();
    }

    @Entao("o Restaurante foi apagado do banco de dados pelo RestauranteController")
    public void o_restaurante_foi_apagado_do_banco_de_dados_pelo_restaurante_controller() {

        var response = restauranteRepository.findById(restauranteEntity.getRestauranteId());

        assertThat(response).isEmpty();
    }

    @Entao("o Endereço foi apagado do banco de dados pelo RestauranteController")
    public void o_endereco_foi_apagado_do_banco_de_dados_pelo_restaurante_controller() {

        var response = enderecoRepository.findById(restauranteEntity.getEndereco().getEnderecoId());

        assertThat(response).isEmpty();
    }

    @Quando("uma requisição Put for feita no método update do RestauranteController")
    public void uma_requisicao_put_for_feita_no_metodo_update_do_restaurante_controller() {

        response = RestAssured
                .given().spec(requestSpecification)
                .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .body(restauranteDtoRequest)
                .when()
                .put("/" + restauranteEntity.getRestauranteId());

        assertThat(response).isNotNull();
    }

    @Dado("um RestauranteDtoRequest, com nome {string} e tipoCozinhaEnum {string}, e EnderecoDtoRequest, com cep {string} e logradouro {string} e número {string},e Proprietario, com Id inexistente")
    public void um_restaurante_dto_request_com_nome_e_endereco_dto_request_com_cep_e_logradouro_e_numero_e_proprietario_com_id_inexistente(
            String nome, String tipoCozinhaEnum, String cep, String logradouro, String numero) {

        restauranteDtoRequest = new RestauranteDtoRequest(
                nome, TipoCozinhaEnum.valueOf(tipoCozinhaEnum),
                new EnderecoDtoRequest(cep, logradouro, numero), UUID.randomUUID()
        );

        assertThat(restauranteDtoRequest).isNotNull();
        assertThat(restauranteDtoRequest.endereco()).isNotNull();
        assertThat(restauranteDtoRequest.proprietario()).isNotNull();
    }

}

