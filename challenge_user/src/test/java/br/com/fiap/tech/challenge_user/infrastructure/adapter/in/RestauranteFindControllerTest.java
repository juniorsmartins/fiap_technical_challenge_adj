package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers.RestauranteFindController;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.OutputMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.application.exception.http404.RecursoNotFoundException;
import br.com.fiap.tech.challenge_user.domain.models.Endereco;
import br.com.fiap.tech.challenge_user.domain.models.Proprietario;
import br.com.fiap.tech.challenge_user.domain.models.Restaurante;
import br.com.fiap.tech.challenge_user.domain.models.enums.TipoCozinhaEnum;
import br.com.fiap.tech.challenge_user.application.dtos.out.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.application.dtos.out.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.application.dtos.out.RestauranteDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.entities.RestauranteEntity;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteFindControllerTest {

    @Mock
    private OutputMapper<Restaurante, RestauranteDtoResponse, RestauranteEntity> outputMapper;

    @Mock
    private FindByIdOutputPort<RestauranteEntity> findByIdOutputPort;

    @InjectMocks
    private RestauranteFindController restauranteFindController;

    private UUID restauranteId;

    private RestauranteEntity restauranteEntity;

    private RestauranteDtoResponse restauranteDtoResponse;

    @BeforeEach
    void setUp() {
        restauranteId = UUID.randomUUID();
        var enderecoId = UUID.randomUUID();
        var proprietarioId = UUID.randomUUID();

        var endereco = new Endereco();
        endereco.setCep("01001-000");
        endereco.setLogradouro("Avenida Central");
        endereco.setNumero("1500");

        var proprietario = new Proprietario(
                proprietarioId, "João Silva", "joao@email.com", "jsilva", "jsilva!123",
                endereco, "Proprietário principal"
        );

        var restaurante = new Restaurante();
        restaurante.setRestauranteId(restauranteId);
        restaurante.setNome("Restaurante Sabor");
        restaurante.setTipoCozinhaEnum(TipoCozinhaEnum.ITALIANA);
        restaurante.setHoraAbertura(LocalTime.of(11, 0));
        restaurante.setHoraFechamento(LocalTime.of(23, 0));
        restaurante.setEndereco(endereco);
        restaurante.setProprietario(proprietario);

        restauranteEntity = new RestauranteEntity();
        restauranteEntity.setRestauranteId(restauranteId);
        restauranteEntity.setNome("Restaurante Sabor");
        restauranteEntity.setTipoCozinhaEnum(TipoCozinhaEnum.ITALIANA);
        restauranteEntity.setHoraAbertura(LocalTime.of(11, 0));
        restauranteEntity.setHoraFechamento(LocalTime.of(23, 0));

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
    void deveEncontrarRestauranteComSucesso() {
        // Arrange
        doReturn(Optional.of(restauranteEntity)).when(findByIdOutputPort).findById(restauranteId);
        doReturn(restauranteDtoResponse).when(outputMapper).toResponse(restauranteEntity);

        // Act
        ResponseEntity<RestauranteDtoResponse> response = restauranteFindController.findById(restauranteId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restauranteDtoResponse, response.getBody());
        verify(findByIdOutputPort, times(1)).findById(restauranteId);
        verify(outputMapper, times(1)).toResponse(restauranteEntity);
        verifyNoMoreInteractions(findByIdOutputPort, outputMapper);
    }

    @Test
    void deveLancarRecursoNotFoundExceptionQuandoRestauranteNaoEncontrado() {
        // Arrange
        doReturn(Optional.empty()).when(findByIdOutputPort).findById(restauranteId);

        // Act & Assert
        RecursoNotFoundException exception = assertThrows(
                RecursoNotFoundException.class,
                () -> restauranteFindController.findById(restauranteId)
        );

        assertEquals(restauranteId, exception.getId());
        verify(findByIdOutputPort, times(1)).findById(restauranteId);
        verifyNoInteractions(outputMapper);
        verifyNoMoreInteractions(findByIdOutputPort);
    }
}

