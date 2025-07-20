package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.application.dtos.in.EnderecoDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.in.RestauranteDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.application.dtos.out.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.application.dtos.out.RestauranteDtoResponse;
import br.com.fiap.tech.challenge_user.application.interfaces.in.CreateInputPort;
import br.com.fiap.tech.challenge_user.domain.entities.Endereco;
import br.com.fiap.tech.challenge_user.domain.entities.Proprietario;
import br.com.fiap.tech.challenge_user.domain.entities.Restaurante;
import br.com.fiap.tech.challenge_user.domain.entities.enums.TipoCozinhaEnum;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers.RestauranteCreateController;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.InputPresenter;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputPresenter;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.RestauranteDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteCreateControllerTest {

    @Mock
    private InputPresenter<RestauranteDtoRequest, Restaurante> inputPresenter;

    @Mock
    private OutputPresenter<Restaurante, RestauranteDtoResponse, RestauranteDao> outputPresenter;

    @Mock
    private CreateInputPort<Restaurante> createInputPort;

    @InjectMocks
    private RestauranteCreateController restauranteCreateController;

    private RestauranteDtoRequest restauranteDtoRequest;

    private Restaurante restaurante;

    private RestauranteDtoResponse restauranteDtoResponse;

    @BeforeEach
    void setUp() {
        var restauranteId = UUID.randomUUID();
        var enderecoId = UUID.randomUUID();
        var proprietarioId = UUID.randomUUID();

        var enderecoDtoRequest = new EnderecoDtoRequest("01001-000", "Avenida Central", "1500");

        restauranteDtoRequest = new RestauranteDtoRequest(
                "Restaurante Sabor",
                TipoCozinhaEnum.ITALIANA,
                LocalTime.of(11, 0),
                LocalTime.of(23, 0),
                enderecoDtoRequest,
                proprietarioId
        );

        var endereco = new Endereco();
        endereco.setCep("01001-000");
        endereco.setLogradouro("Avenida Central");
        endereco.setNumero("1500");

        var proprietario = new Proprietario(
                proprietarioId, "João Silva", "joao@email.com", "jsilva", "jsilva!123",
                endereco, "Proprietário principal"
        );

        restaurante = new Restaurante();
        restaurante.setRestauranteId(restauranteId);
        restaurante.setNome("Restaurante Sabor");
        restaurante.setTipoCozinhaEnum(TipoCozinhaEnum.ITALIANA);
        restaurante.setHoraAbertura(LocalTime.of(11, 0));
        restaurante.setHoraFechamento(LocalTime.of(23, 0));
        restaurante.setEndereco(endereco);
        restaurante.setProprietario(proprietario);

        var enderecoDtoResponse = new EnderecoDtoResponse(
                enderecoId, "01001-000", "Avenida Central", "1500"
        );

        var proprietarioDtoResponse = new ProprietarioDtoResponse(
                proprietarioId, "João Silva", "joao@email.com", "jsilva", "jsilva!123",
                Date.from(Instant.now()), null, enderecoDtoResponse, "Proprietário principal"
        );

        restauranteDtoResponse = new RestauranteDtoResponse(
                restauranteId, "Restaurante Sabor", TipoCozinhaEnum.ITALIANA,
                LocalTime.of(11, 0), LocalTime.of(23, 0),
                enderecoDtoResponse, proprietarioDtoResponse
        );
    }

    @Test
    void deveCriarRestauranteComSucesso() {
        // Arrange
        doReturn(restaurante).when(inputPresenter).toDomainIn(restauranteDtoRequest);
        doReturn(restaurante).when(createInputPort).create(restaurante);
        doReturn(restauranteDtoResponse).when(outputPresenter).toDtoResponse(restaurante);

        // Act
        ResponseEntity<RestauranteDtoResponse> response = restauranteCreateController.create(restauranteDtoRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(restauranteDtoResponse, response.getBody());
        verify(inputPresenter, times(1)).toDomainIn(restauranteDtoRequest);
        verify(createInputPort, times(1)).create(restaurante);
        verify(outputPresenter, times(1)).toDtoResponse(restaurante);
        verifyNoMoreInteractions(inputPresenter, createInputPort, outputPresenter);
    }
}

