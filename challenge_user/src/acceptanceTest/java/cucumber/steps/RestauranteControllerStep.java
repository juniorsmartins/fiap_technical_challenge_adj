package cucumber.steps;

import br.com.fiap.tech.challenge_user.application.dtos.in.EnderecoDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.in.RestauranteDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.application.dtos.out.RestauranteDtoResponse;
import br.com.fiap.tech.challenge_user.domain.entities.enums.TipoCozinhaEnum;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.ProprietarioPresenter;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ClienteDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.EnderecoDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.RestauranteDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.repositories.*;
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
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    private RestauranteDao restauranteDao;

    private EnderecoDao enderecoDao;

    private EnderecoDtoResponse enderecoDtoResponse;

    private ProprietarioPresenter proprietarioMapper;

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

            var clienteEntidade = new ClienteDao(
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

            var entidade = new ProprietarioDao(
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

            enderecoDao = new EnderecoDao();
            enderecoDao.setCep(row.get("cep"));
            enderecoDao.setLogradouro(row.get("logradouro"));
            enderecoDao.setNumero(row.get("numero"));

            var proprietarioEntity = proprietarioRepository.findByEmail(row.get("proprietario")).get();

            var entidade = new RestauranteDao();
            entidade.setNome(row.get("nome"));
            entidade.setTipoCozinhaEnum(TipoCozinhaEnum.valueOf(row.get("tipoCozinhaEnum")));
            entidade.setHoraAbertura(LocalTime.parse(row.get("horaAbertura")));
            entidade.setHoraFechamento(LocalTime.parse(row.get("horaFechamento")));
            entidade.setEndereco(enderecoDao);
            entidade.setProprietario(proprietarioEntity);

            restauranteRepository.save(entidade);
        }
    }

    @Dado("um RestauranteDtoRequest, com nome {string} e tipoCozinhaEnum {string} e horaAbertura {string} e horaFechamento {string}, e EnderecoDtoRequest, com cep {string} e logradouro {string} e número {string},e Proprietario, com email {string}")
    public void um_restaurante_dto_request_com_nome_e_endereco_dto_request_com_cep_e_logradouro_e_numero_e_proprietario_com_email(
            String nome, String tipoCozinhaEnum, String horaAbertura, String horaFechamento, String cep, String logradouro, String numero, String email) {

        var proprietario = usuarioRepository.findByEmail(email).get();

        restauranteDtoRequest = new RestauranteDtoRequest(
                nome, TipoCozinhaEnum.valueOf(tipoCozinhaEnum),
                LocalTime.parse(horaAbertura), LocalTime.parse(horaFechamento),
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

    @Entao("com RestauranteDtoResponse no body, com id e nome {string} e tipoCozinhaEnum {string} e horaAbertura {string} e horaFechamento {string}")
    public void com_restaurante_dto_response_no_body_com_id_e_nome(
            String nome, String tipoCozinhaEnum, String horaAbertura, String horaFechamento) {

        restauranteDtoResponse = response.as(RestauranteDtoResponse.class);

        assertThat(restauranteDtoResponse.restauranteId()).isNotNull();
        assertThat(restauranteDtoResponse.nome()).isEqualTo(nome);
        assertThat(restauranteDtoResponse.tipoCozinhaEnum()).isEqualTo(TipoCozinhaEnum.valueOf(tipoCozinhaEnum));
        assertThat(restauranteDtoResponse.horaAbertura()).isEqualTo(LocalTime.parse(horaAbertura));
        assertThat(restauranteDtoResponse.horaFechamento()).isEqualTo(LocalTime.parse(horaFechamento));
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

    @Entao("o Restaurante cadastrado no banco de dados possui nome {string} e tipoCozinhaEnum {string} e horaAbertura {string} e horaFechamento {string}")
    public void o_restaurante_cadastrado_no_banco_de_dados_possui_nome(
            String nome, String tipoCozinhaEnum, String horaAbertura, String horaFechamento) {

        var entidade = restauranteRepository.findById(restauranteDtoResponse.restauranteId()).get();

        assertThat(entidade.getNome()).isEqualTo(nome);
        assertThat(entidade.getTipoCozinhaEnum()).isEqualTo(TipoCozinhaEnum.valueOf(tipoCozinhaEnum));
        assertThat(entidade.getHoraAbertura()).isEqualTo(LocalTime.parse(horaAbertura));
        assertThat(entidade.getHoraFechamento()).isEqualTo(LocalTime.parse(horaFechamento));
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

        restauranteDao = restauranteRepository.findByNome(nome).get();

        assertThat(restauranteDao).isNotNull();
    }

    @Quando("uma requisição Get for feita no método findById do RestauranteController")
    public void uma_requisicao_get_for_feita_no_metodo_find_by_id_do_restaurante_controller() {

        response = RestAssured
                .given().spec(requestSpecification)
                .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .when()
                .get("/" + restauranteDao.getRestauranteId());

        assertThat(response).isNotNull();
    }

    @Dado("um identificador ID de um Restaurante inexistente")
    public void um_identificador_id_de_um_restaurante_inexistente() {

        var proprietario = proprietarioRepository.findByEmail("galilei@yahoo.com").get();

        restauranteDao = new RestauranteDao(UUID.randomUUID(), "nomeTeste", TipoCozinhaEnum.ARABE,
                LocalTime.of(8, 10, 10), LocalTime.of(21, 00, 10),
                new EnderecoDao(UUID.randomUUID(), "78000-000", "Rua Teste", "300"),
                proprietario);

        assertThat(restauranteDao.getRestauranteId()).isNotNull();
    }

    @Quando("uma requisição Delete for feita no método deleteById do RestauranteController")
    public void uma_requisicao_delete_for_feita_no_metodo_delete_by_id_do_restaurante_controller() {

        response = RestAssured
                .given().spec(requestSpecification)
                .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .when()
                .delete("/" + restauranteDao.getRestauranteId());

        assertThat(response).isNotNull();
    }

    @Entao("o Restaurante foi apagado do banco de dados pelo RestauranteController")
    public void o_restaurante_foi_apagado_do_banco_de_dados_pelo_restaurante_controller() {

        var response = restauranteRepository.findById(restauranteDao.getRestauranteId());

        assertThat(response).isEmpty();
    }

    @Entao("o Endereço foi apagado do banco de dados pelo RestauranteController")
    public void o_endereco_foi_apagado_do_banco_de_dados_pelo_restaurante_controller() {

        var response = enderecoRepository.findById(restauranteDao.getEndereco().getEnderecoId());

        assertThat(response).isEmpty();
    }

    @Quando("uma requisição Put for feita no método update do RestauranteController")
    public void uma_requisicao_put_for_feita_no_metodo_update_do_restaurante_controller() {

        response = RestAssured
                .given().spec(requestSpecification)
                .contentType(ConstantsTest.CONTENT_TYPE_JSON)
                .body(restauranteDtoRequest)
                .when()
                .put("/" + restauranteDao.getRestauranteId());

        assertThat(response).isNotNull();
    }

    @Dado("um RestauranteDtoRequest, com nome {string} e tipoCozinhaEnum {string} e horaAbertura {string} e horaFechamento {string}, e EnderecoDtoRequest, com cep {string} e logradouro {string} e número {string},e Proprietario, com Id inexistente")
    public void um_restaurante_dto_request_com_nome_e_endereco_dto_request_com_cep_e_logradouro_e_numero_e_proprietario_com_id_inexistente(
            String nome, String tipoCozinhaEnum, String horaAbertura, String horaFechamento, String cep, String logradouro, String numero) {

        restauranteDtoRequest = new RestauranteDtoRequest(
                nome, TipoCozinhaEnum.valueOf(tipoCozinhaEnum),
                LocalTime.parse(horaAbertura), LocalTime.parse(horaFechamento),
                new EnderecoDtoRequest(cep, logradouro, numero), UUID.randomUUID()
        );

        assertThat(restauranteDtoRequest).isNotNull();
        assertThat(restauranteDtoRequest.endereco()).isNotNull();
        assertThat(restauranteDtoRequest.proprietario()).isNotNull();
    }

}

