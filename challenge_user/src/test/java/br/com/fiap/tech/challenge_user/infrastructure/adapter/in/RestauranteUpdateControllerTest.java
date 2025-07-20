package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers.RestauranteUpdateController;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.InputMapper;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.in.UpdateInputPort;
import br.com.fiap.tech.challenge_user.application.exception.http500.InternalServerProblemException;
import br.com.fiap.tech.challenge_user.domain.models.Endereco;
import br.com.fiap.tech.challenge_user.domain.models.Proprietario;
import br.com.fiap.tech.challenge_user.domain.models.Restaurante;
import br.com.fiap.tech.challenge_user.domain.models.enums.TipoCozinhaEnum;
import br.com.fiap.tech.challenge_user.application.dtos.in.EnderecoDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.in.RestauranteDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.application.dtos.out.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.application.dtos.out.RestauranteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.RestauranteDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteUpdateControllerTest {

    @Mock
    private InputMapper<RestauranteDtoRequest, Restaurante> inputMapper;

    @Mock
    private OutputMapper<Restaurante, RestauranteDtoResponse, RestauranteDao> outputMapper;

    @Mock
    private UpdateInputPort<Restaurante> updateInputPort;

    @InjectMocks
    private RestauranteUpdateController restauranteUpdateController;

    private RestauranteDtoRequest restauranteDtoRequest;

    private Restaurante restaurante;

    private RestauranteDtoResponse restauranteDtoResponse;

    private UUID restauranteId;

    @BeforeEach
    void setUp() {
        restauranteId = UUID.randomUUID();
        var enderecoId = UUID.randomUUID();
        var proprietarioId = UUID.randomUUID();

        var enderecoDtoRequest = new EnderecoDtoRequest("01001-000", "Avenida Central", "1500");

        restauranteDtoRequest = new RestauranteDtoRequest(
                "Restaurante Sabor", TipoCozinhaEnum.ITALIANA,
                LocalTime.of(11, 0), LocalTime.of(23, 0),
                enderecoDtoRequest, proprietarioId
        );

        var endereco = new Endereco();
        endereco.setCep("01001-000");
        endereco.setLogradouro("Avenida Central");
        endereco.setNumero("1500");

        var proprietario = new Proprietario(
                proprietarioId, "João Silva", "joao@email.com", "jsilva", "jsilva!123",
                endereco, "Proprietário principal"
        );

        restaurante = new Restaurante(
                restauranteId, "Restaurante Sabor", TipoCozinhaEnum.ITALIANA,
                LocalTime.of(11, 0), LocalTime.of(23, 0),
                endereco, proprietario
        );

        var enderecoDtoResponse = new EnderecoDtoResponse(
                enderecoId, "01001-000", "Avenida Central", "1500"
        );

        var proprietarioDtoResponse = new ProprietarioDtoResponse(
                proprietarioId, "João Silva", "joao@email.com", "jsilva", "jsilva!123",
                null, null, enderecoDtoResponse, "Proprietário principal"
        );

        restauranteDtoResponse = new RestauranteDtoResponse(
                restauranteId, "Restaurante Sabor", TipoCozinhaEnum.ITALIANA,
                LocalTime.of(11, 0), LocalTime.of(23, 0),
                enderecoDtoResponse, proprietarioDtoResponse
        );
    }

    @Test
    void deveAtualizarRestauranteComSucesso() {
        // Arrange
        doReturn(restaurante).when(inputMapper).toDomainIn(restauranteDtoRequest);
        doReturn(restaurante).when(updateInputPort).update(restauranteId, restaurante);
        doReturn(restauranteDtoResponse).when(outputMapper).toDtoResponse(restaurante);

        // Act
        ResponseEntity<RestauranteDtoResponse> response = restauranteUpdateController.update(restauranteId, restauranteDtoRequest);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restauranteDtoResponse, response.getBody());
        verify(inputMapper, times(1)).toDomainIn(restauranteDtoRequest);
        verify(updateInputPort, times(1)).update(restauranteId, restaurante);
        verify(outputMapper, times(1)).toDtoResponse(restaurante);
        verifyNoMoreInteractions(inputMapper, updateInputPort, outputMapper);
    }

    @Test
    void deveLancarInternalServerProblemExceptionQuandoDtoRequestEhNulo() {
        // Act & Assert
        InternalServerProblemException exception = assertThrows(
                InternalServerProblemException.class,
                () -> restauranteUpdateController.update(restauranteId, null)
        );

        assertEquals("exception.internal.server.error", exception.getMessage());
        verifyNoInteractions(inputMapper, updateInputPort, outputMapper);
    }
}

