package br.com.fiap.tech.challenge_user.application.mapper;

import br.com.fiap.tech.challenge_user.domain.entities.Cliente;
import br.com.fiap.tech.challenge_user.domain.entities.Endereco;
import br.com.fiap.tech.challenge_user.application.dtos.in.ClienteDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.in.EnderecoDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ClienteDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.EnderecoDao;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.ClientePresenter;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.EnderecoPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteMapperTest {

    @Mock
    private EnderecoPresenter enderecoPresenter;

    @InjectMocks
    private ClientePresenter clienteMapper;

    private ClienteDtoRequest clienteDtoRequest;
    private Cliente cliente;
    private ClienteDao clienteDao;
    private Endereco endereco;
    private EnderecoDtoResponse enderecoDtoResponse;

    @BeforeEach
    void setUp() {
        var usuarioId = UUID.randomUUID();
        var enderecoId = UUID.randomUUID();
        var dataHoraCriacao = Date.from(Instant.now());
        var dataHoraEdicao = Date.from(Instant.now().plusSeconds(3600));

        var enderecoDtoRequest = new EnderecoDtoRequest(
                "01001-000", "Avenida Central", "1500"
        );

        clienteDtoRequest = new ClienteDtoRequest(
                "Ana Silva", "ana@email.com", "asilva", "asilva!123",
                enderecoDtoRequest, "123456789"
        );

        endereco = new Endereco();
        endereco.setEnderecoId(enderecoId);
        endereco.setCep("01001-000");
        endereco.setLogradouro("Avenida Central");
        endereco.setNumero("1500");

        cliente = new Cliente(
                usuarioId, "Ana Silva", "ana@email.com", "asilva", "asilva!123",
                endereco, "123456789"
        );

        var enderecoEntity = new EnderecoDao(
                enderecoId, "01001-000", "Avenida Central", "1500"
        );

        clienteDao = new ClienteDao(
                usuarioId, "Ana Silva", "ana@email.com", "asilva", "asilva!123",
                enderecoEntity, "123456789", dataHoraCriacao, dataHoraEdicao
        );

        enderecoDtoResponse = new EnderecoDtoResponse(
                enderecoId, "01001-000", "Avenida Central", "1500"
        );
    }

    @Test
    void deveMapearClienteDtoRequestParaCliente() {
        // Arrange
        when(enderecoPresenter.toEndereco(clienteDtoRequest.endereco())).thenReturn(endereco);

        // Act
        var result = clienteMapper.toDomainIn(clienteDtoRequest);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUsuarioId()).isNull();
        assertThat(result.getNome()).isEqualTo(clienteDtoRequest.nome());
        assertThat(result.getEmail()).isEqualTo(clienteDtoRequest.email());
        assertThat(result.getLogin()).isEqualTo(clienteDtoRequest.login());
        assertThat(result.getSenha()).isEqualTo(clienteDtoRequest.senha());
        assertThat(result.getEndereco()).isEqualTo(endereco);
        assertThat(result.getNumeroCartaoFidelidade()).isEqualTo(clienteDtoRequest.numeroCartaoFidelidade());
        verify(enderecoPresenter).toEndereco(clienteDtoRequest.endereco());
        verifyNoMoreInteractions(enderecoPresenter);
    }

    @Test
    void deveMapearClienteParaClienteEntity() {
        // Arrange
        when(enderecoPresenter.toEnderecoEntity(cliente.getEndereco())).thenReturn(clienteDao.getEndereco());

        // Act
        var result = clienteMapper.toEntity(cliente);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUsuarioId()).isEqualTo(cliente.getUsuarioId());
        assertThat(result.getNome()).isEqualTo(cliente.getNome());
        assertThat(result.getEmail()).isEqualTo(cliente.getEmail());
        assertThat(result.getLogin()).isEqualTo(cliente.getLogin());
        assertThat(result.getSenha()).isEqualTo(cliente.getSenha());
        assertThat(result.getEndereco()).isEqualTo(clienteDao.getEndereco());
        assertThat(result.getNumeroCartaoFidelidade()).isEqualTo(cliente.getNumeroCartaoFidelidade());
        assertThat(result.getDataHoraCriacao()).isNull();
        assertThat(result.getDataHoraEdicao()).isNull();
        verify(enderecoPresenter).toEnderecoEntity(cliente.getEndereco());
        verifyNoMoreInteractions(enderecoPresenter);
    }

    @Test
    void deveMapearClienteEntityParaCliente() {
        // Arrange
        when(enderecoPresenter.toEndereco(clienteDao.getEndereco())).thenReturn(endereco);

        // Act
        var result = clienteMapper.toDomain(clienteDao);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUsuarioId()).isEqualTo(clienteDao.getUsuarioId());
        assertThat(result.getNome()).isEqualTo(clienteDao.getNome());
        assertThat(result.getEmail()).isEqualTo(clienteDao.getEmail());
        assertThat(result.getLogin()).isEqualTo(clienteDao.getLogin());
        assertThat(result.getSenha()).isEqualTo(clienteDao.getSenha());
        assertThat(result.getEndereco()).isEqualTo(endereco);
        assertThat(result.getNumeroCartaoFidelidade()).isEqualTo(clienteDao.getNumeroCartaoFidelidade());
        verify(enderecoPresenter).toEndereco(clienteDao.getEndereco());
        verifyNoMoreInteractions(enderecoPresenter);
    }

    @Test
    void deveMapearClienteParaClienteDtoResponse() {
        // Arrange
        when(enderecoPresenter.toEnderecoDtoResponse(cliente.getEndereco())).thenReturn(enderecoDtoResponse);

        // Act
        var result = clienteMapper.toDtoResponse(cliente);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.usuarioId()).isEqualTo(cliente.getUsuarioId());
        assertThat(result.nome()).isEqualTo(cliente.getNome());
        assertThat(result.email()).isEqualTo(cliente.getEmail());
        assertThat(result.login()).isEqualTo(cliente.getLogin());
        assertThat(result.senha()).isEqualTo(cliente.getSenha());
        assertThat(result.endereco()).isEqualTo(enderecoDtoResponse);
        assertThat(result.numeroCartaoFidelidade()).isEqualTo(cliente.getNumeroCartaoFidelidade());
        assertThat(result.dataHoraCriacao()).isNull();
        assertThat(result.dataHoraEdicao()).isNull();
        verify(enderecoPresenter).toEnderecoDtoResponse(cliente.getEndereco());
        verifyNoMoreInteractions(enderecoPresenter);
    }

    @Test
    void deveMapearClienteEntityParaClienteDtoResponse() {
        // Arrange
        when(enderecoPresenter.toEnderecoDtoResponse(clienteDao.getEndereco())).thenReturn(enderecoDtoResponse);

        // Act
        var result = clienteMapper.toResponse(clienteDao);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.usuarioId()).isEqualTo(clienteDao.getUsuarioId());
        assertThat(result.nome()).isEqualTo(clienteDao.getNome());
        assertThat(result.email()).isEqualTo(clienteDao.getEmail());
        assertThat(result.login()).isEqualTo(clienteDao.getLogin());
        assertThat(result.senha()).isEqualTo(clienteDao.getSenha());
        assertThat(result.dataHoraCriacao()).isEqualTo(clienteDao.getDataHoraCriacao());
        assertThat(result.dataHoraEdicao()).isEqualTo(clienteDao.getDataHoraEdicao());
        assertThat(result.endereco()).isEqualTo(enderecoDtoResponse);
        assertThat(result.numeroCartaoFidelidade()).isEqualTo(clienteDao.getNumeroCartaoFidelidade());
        verify(enderecoPresenter).toEnderecoDtoResponse(clienteDao.getEndereco());
        verifyNoMoreInteractions(enderecoPresenter);
    }

    @Test
    void deveMapearPageClienteEntityParaPageClienteDtoResponse() {
        // Arrange
        when(enderecoPresenter.toEnderecoDtoResponse(clienteDao.getEndereco())).thenReturn(enderecoDtoResponse);
        var page = new PageImpl<>(List.of(clienteDao), PageRequest.of(0, 10), 1);

        // Act
        var result = clienteMapper.toPageResponse(page);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);
        var resultDto = result.getContent().getFirst();
        assertThat(resultDto.usuarioId()).isEqualTo(clienteDao.getUsuarioId());
        assertThat(resultDto.nome()).isEqualTo(clienteDao.getNome());
        assertThat(resultDto.email()).isEqualTo(clienteDao.getEmail());
        assertThat(resultDto.login()).isEqualTo(clienteDao.getLogin());
        assertThat(resultDto.senha()).isEqualTo(clienteDao.getSenha());
        assertThat(resultDto.dataHoraCriacao()).isEqualTo(clienteDao.getDataHoraCriacao());
        assertThat(resultDto.dataHoraEdicao()).isEqualTo(clienteDao.getDataHoraEdicao());
        assertThat(resultDto.endereco()).isEqualTo(enderecoDtoResponse);
        assertThat(resultDto.numeroCartaoFidelidade()).isEqualTo(clienteDao.getNumeroCartaoFidelidade());
        verify(enderecoPresenter).toEnderecoDtoResponse(clienteDao.getEndereco());
        verifyNoMoreInteractions(enderecoPresenter);
    }
}

