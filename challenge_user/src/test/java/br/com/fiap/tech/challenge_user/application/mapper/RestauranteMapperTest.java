package br.com.fiap.tech.challenge_user.application.mapper;

import br.com.fiap.tech.challenge_user.domain.model.Endereco;
import br.com.fiap.tech.challenge_user.domain.model.Proprietario;
import br.com.fiap.tech.challenge_user.domain.model.Restaurante;
import br.com.fiap.tech.challenge_user.domain.model.enums.TipoCozinhaEnum;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.EnderecoDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.RestauranteDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import br.com.fiap.tech.challenge_user.infrastructure.entity.RestauranteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteMapperTest {

    @Mock
    private EnderecoMapper enderecoMapper;

    @Mock
    private ProprietarioMapper proprietarioMapper;

    @InjectMocks
    private RestauranteMapperImpl restauranteMapper;

    private RestauranteDtoRequest restauranteDtoRequest;

    private Restaurante restaurante;

    private RestauranteEntity restauranteEntity;

    private Endereco endereco;

    private EnderecoDtoResponse enderecoDtoResponse;

    private Proprietario proprietario;

    private ProprietarioEntity proprietarioEntity;

    private ProprietarioDtoResponse proprietarioDtoResponse;

    @BeforeEach
    void setUp() {
        var restauranteId = UUID.randomUUID();
        var enderecoId = UUID.randomUUID();
        var proprietarioId = UUID.randomUUID();
        var horaAbertura = LocalTime.of(8, 10, 10);
        var horaFechamento = LocalTime.of(20, 5, 12);
        var dataHoraCriacao = Date.from(Instant.now());
        var dataHoraEdicao = Date.from(Instant.now().plusSeconds(60000));

        var enderecoDtoRequest = new EnderecoDtoRequest(
                "01001-000", "Avenida Central", "1500"
        );

        restauranteDtoRequest = new RestauranteDtoRequest(
                "Casa das Aves",
                TipoCozinhaEnum.ITALIANA,
                LocalTime.of(8, 0),
                LocalTime.of(22, 0),
                enderecoDtoRequest,
                proprietarioId
        );

        endereco = new Endereco();
        endereco.setEnderecoId(enderecoId);
        endereco.setCep("01001-000");
        endereco.setLogradouro("Avenida Central");
        endereco.setNumero("1500");

        proprietario = new Proprietario();
        proprietario.setUsuarioId(proprietarioId);

        restaurante = new Restaurante(
                restauranteId,
                "Casa das Aves",
                TipoCozinhaEnum.ITALIANA,
                LocalTime.of(8, 0),
                LocalTime.of(22, 0),
                endereco,
                proprietario
        );

        var enderecoEntity = new EnderecoEntity(
                enderecoId, "01001-000", "Avenida Central", "1500"
        );

        proprietarioEntity = new ProprietarioEntity(
                proprietarioId, "João Silva", "joao@email.com", "jsilva", "jsilva!123",
                enderecoEntity, "descrição", Date.from(Instant.now()), Date.from(Instant.now().plusSeconds(60000))
        );

        restauranteEntity = new RestauranteEntity(
                restauranteId,
                "Casa das Aves",
                TipoCozinhaEnum.ITALIANA,
                horaAbertura,
                horaFechamento,
                enderecoEntity,
                proprietarioEntity
        );

        enderecoDtoResponse = new EnderecoDtoResponse(
                enderecoId, "01001-000", "Avenida Central", "1500"
        );

        proprietarioDtoResponse = new ProprietarioDtoResponse(
                proprietarioId, "João Silva", "joao@email.com", "jsilva", "jsilva!123",
                dataHoraCriacao, dataHoraEdicao, enderecoDtoResponse, "descrição"
        );
    }

    @Test
    void deveMapearRestauranteDtoRequestParaRestaurante() {
        // Arrange
        when(enderecoMapper.toEndereco(restauranteDtoRequest.endereco())).thenReturn(endereco);

        // Act
        var result = restauranteMapper.toDomainIn(restauranteDtoRequest);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getRestauranteId()).isNull();
        assertThat(result.getNome()).isEqualTo(restauranteDtoRequest.nome());
        assertThat(result.getTipoCozinhaEnum()).isEqualTo(restauranteDtoRequest.tipoCozinhaEnum());
        assertThat(result.getHoraAbertura()).isEqualTo(restauranteDtoRequest.horaAbertura());
        assertThat(result.getHoraFechamento()).isEqualTo(restauranteDtoRequest.horaFechamento());
        assertThat(result.getEndereco()).isEqualTo(endereco);
        assertThat(result.getProprietario().getUsuarioId()).isEqualTo(restauranteDtoRequest.proprietario());
        verify(enderecoMapper).toEndereco(restauranteDtoRequest.endereco());
        verifyNoMoreInteractions(enderecoMapper, proprietarioMapper);
    }

    @Test
    void deveMapearRestauranteParaRestauranteEntity() {
        // Arrange
        when(enderecoMapper.toEnderecoEntity(restaurante.getEndereco())).thenReturn(restauranteEntity.getEndereco());
        when(proprietarioMapper.toEntity(restaurante.getProprietario())).thenReturn(proprietarioEntity);

        // Act
        var result = restauranteMapper.toEntity(restaurante);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getRestauranteId()).isEqualTo(restaurante.getRestauranteId());
        assertThat(result.getNome()).isEqualTo(restaurante.getNome());
        assertThat(result.getTipoCozinhaEnum()).isEqualTo(restaurante.getTipoCozinhaEnum());
        assertThat(result.getHoraAbertura()).isEqualTo(restaurante.getHoraAbertura());
        assertThat(result.getHoraFechamento()).isEqualTo(restaurante.getHoraFechamento());
        assertThat(result.getEndereco()).isEqualTo(restauranteEntity.getEndereco());
        assertThat(result.getProprietario()).isEqualTo(proprietarioEntity);
        verify(enderecoMapper).toEnderecoEntity(restaurante.getEndereco());
        verify(proprietarioMapper).toEntity(restaurante.getProprietario());
        verifyNoMoreInteractions(enderecoMapper, proprietarioMapper);
    }

    @Test
    void deveMapearRestauranteEntityParaRestaurante() {
        // Arrange
        when(enderecoMapper.toEndereco(restauranteEntity.getEndereco())).thenReturn(endereco);
        when(proprietarioMapper.toDomain(restauranteEntity.getProprietario())).thenReturn(proprietario);

        // Act
        var result = restauranteMapper.toDomain(restauranteEntity);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getRestauranteId()).isEqualTo(restauranteEntity.getRestauranteId());
        assertThat(result.getNome()).isEqualTo(restauranteEntity.getNome());
        assertThat(result.getTipoCozinhaEnum()).isEqualTo(restauranteEntity.getTipoCozinhaEnum());
        assertThat(result.getHoraAbertura()).isEqualTo(restauranteEntity.getHoraAbertura());
        assertThat(result.getHoraFechamento()).isEqualTo(restauranteEntity.getHoraFechamento());
        assertThat(result.getEndereco()).isEqualTo(endereco);
        assertThat(result.getProprietario()).isEqualTo(proprietario);
        verify(enderecoMapper).toEndereco(restauranteEntity.getEndereco());
        verify(proprietarioMapper).toDomain(restauranteEntity.getProprietario());
        verifyNoMoreInteractions(enderecoMapper, proprietarioMapper);
    }

    @Test
    void deveMapearRestauranteParaRestauranteDtoResponse() {
        // Arrange
        when(enderecoMapper.toEnderecoDtoResponse(restaurante.getEndereco())).thenReturn(enderecoDtoResponse);
        when(proprietarioMapper.toDtoResponse(restaurante.getProprietario())).thenReturn(proprietarioDtoResponse);

        // Act
        var result = restauranteMapper.toDtoResponse(restaurante);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.restauranteId()).isEqualTo(restaurante.getRestauranteId());
        assertThat(result.nome()).isEqualTo(restaurante.getNome());
        assertThat(result.tipoCozinhaEnum()).isEqualTo(restaurante.getTipoCozinhaEnum());
        assertThat(result.horaAbertura()).isEqualTo(restaurante.getHoraAbertura());
        assertThat(result.horaFechamento()).isEqualTo(restaurante.getHoraFechamento());
        assertThat(result.endereco()).isEqualTo(enderecoDtoResponse);
        assertThat(result.proprietario()).isEqualTo(proprietarioDtoResponse);
        verify(enderecoMapper).toEnderecoDtoResponse(restaurante.getEndereco());
        verify(proprietarioMapper).toDtoResponse(restaurante.getProprietario());
        verifyNoMoreInteractions(enderecoMapper, proprietarioMapper);
    }

    @Test
    void deveMapearRestauranteEntityParaRestauranteDtoResponse() {
        // Arrange
        when(enderecoMapper.toEnderecoDtoResponse(restauranteEntity.getEndereco())).thenReturn(enderecoDtoResponse);
        when(proprietarioMapper.toResponse(restauranteEntity.getProprietario())).thenReturn(proprietarioDtoResponse);

        // Act
        var result = restauranteMapper.toResponse(restauranteEntity);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.restauranteId()).isEqualTo(restauranteEntity.getRestauranteId());
        assertThat(result.nome()).isEqualTo(restauranteEntity.getNome());
        assertThat(result.tipoCozinhaEnum()).isEqualTo(restauranteEntity.getTipoCozinhaEnum());
        assertThat(result.horaAbertura()).isEqualTo(restauranteEntity.getHoraAbertura());
        assertThat(result.horaFechamento()).isEqualTo(restauranteEntity.getHoraFechamento());
        assertThat(result.endereco()).isEqualTo(enderecoDtoResponse);
        assertThat(result.proprietario()).isEqualTo(proprietarioDtoResponse);
        assertThat(result.horaAbertura()).isEqualTo(restauranteEntity.getHoraAbertura());
        assertThat(result.horaFechamento()).isEqualTo(restauranteEntity.getHoraFechamento());
        verify(enderecoMapper).toEnderecoDtoResponse(restauranteEntity.getEndereco());
        verify(proprietarioMapper).toResponse(restauranteEntity.getProprietario());
        verifyNoMoreInteractions(enderecoMapper, proprietarioMapper);
    }

    @Test
    void deveMapearPageRestauranteEntityParaPageRestauranteDtoResponse() {
        // Arrange
        when(enderecoMapper.toEnderecoDtoResponse(restauranteEntity.getEndereco())).thenReturn(enderecoDtoResponse);
        when(proprietarioMapper.toResponse(restauranteEntity.getProprietario())).thenReturn(proprietarioDtoResponse);
        var page = new PageImpl<>(List.of(restauranteEntity), PageRequest.of(0, 10), 1);

        // Act
        var result = restauranteMapper.toPageResponse(page);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);
        var resultDto = result.getContent().getFirst();
        assertThat(resultDto.restauranteId()).isEqualTo(restauranteEntity.getRestauranteId());
        assertThat(resultDto.nome()).isEqualTo(restauranteEntity.getNome());
        assertThat(resultDto.tipoCozinhaEnum()).isEqualTo(restauranteEntity.getTipoCozinhaEnum());
        assertThat(resultDto.horaAbertura()).isEqualTo(restauranteEntity.getHoraAbertura());
        assertThat(resultDto.horaFechamento()).isEqualTo(restauranteEntity.getHoraFechamento());
        assertThat(resultDto.endereco()).isEqualTo(enderecoDtoResponse);
        assertThat(resultDto.proprietario()).isEqualTo(proprietarioDtoResponse);
        assertThat(resultDto.horaAbertura()).isEqualTo(restauranteEntity.getHoraAbertura());
        assertThat(resultDto.horaFechamento()).isEqualTo(restauranteEntity.getHoraFechamento());
        verify(enderecoMapper).toEnderecoDtoResponse(restauranteEntity.getEndereco());
        verify(proprietarioMapper).toResponse(restauranteEntity.getProprietario());
        verifyNoMoreInteractions(enderecoMapper, proprietarioMapper);
    }
}

