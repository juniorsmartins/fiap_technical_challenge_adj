package br.com.fiap.tech.challenge_user.application.mapper;

import br.com.fiap.tech.challenge_user.domain.model.Cliente;
import br.com.fiap.tech.challenge_user.domain.model.Endereco;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.ClienteDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.in.EnderecoDtoRequest;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ClienteEntity;
import br.com.fiap.tech.challenge_user.infrastructure.entity.EnderecoEntity;
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
    private EnderecoMapper enderecoMapper;

    @InjectMocks
    private ClienteMapper clienteMapper;

    private ClienteDtoRequest clienteDtoRequest;
    private Cliente cliente;
    private ClienteEntity clienteEntity;
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

        var enderecoEntity = new EnderecoEntity(
                enderecoId, "01001-000", "Avenida Central", "1500"
        );

        clienteEntity = new ClienteEntity(
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
        when(enderecoMapper.toEndereco(clienteDtoRequest.endereco())).thenReturn(endereco);

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
        verify(enderecoMapper).toEndereco(clienteDtoRequest.endereco());
        verifyNoMoreInteractions(enderecoMapper);
    }

    @Test
    void deveMapearClienteParaClienteEntity() {
        // Arrange
        when(enderecoMapper.toEnderecoEntity(cliente.getEndereco())).thenReturn(clienteEntity.getEndereco());

        // Act
        var result = clienteMapper.toEntity(cliente);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUsuarioId()).isEqualTo(cliente.getUsuarioId());
        assertThat(result.getNome()).isEqualTo(cliente.getNome());
        assertThat(result.getEmail()).isEqualTo(cliente.getEmail());
        assertThat(result.getLogin()).isEqualTo(cliente.getLogin());
        assertThat(result.getSenha()).isEqualTo(cliente.getSenha());
        assertThat(result.getEndereco()).isEqualTo(clienteEntity.getEndereco());
        assertThat(result.getNumeroCartaoFidelidade()).isEqualTo(cliente.getNumeroCartaoFidelidade());
        assertThat(result.getDataHoraCriacao()).isNull();
        assertThat(result.getDataHoraEdicao()).isNull();
        verify(enderecoMapper).toEnderecoEntity(cliente.getEndereco());
        verifyNoMoreInteractions(enderecoMapper);
    }

    @Test
    void deveMapearClienteEntityParaCliente() {
        // Arrange
        when(enderecoMapper.toEndereco(clienteEntity.getEndereco())).thenReturn(endereco);

        // Act
        var result = clienteMapper.toDomain(clienteEntity);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUsuarioId()).isEqualTo(clienteEntity.getUsuarioId());
        assertThat(result.getNome()).isEqualTo(clienteEntity.getNome());
        assertThat(result.getEmail()).isEqualTo(clienteEntity.getEmail());
        assertThat(result.getLogin()).isEqualTo(clienteEntity.getLogin());
        assertThat(result.getSenha()).isEqualTo(clienteEntity.getSenha());
        assertThat(result.getEndereco()).isEqualTo(endereco);
        assertThat(result.getNumeroCartaoFidelidade()).isEqualTo(clienteEntity.getNumeroCartaoFidelidade());
        verify(enderecoMapper).toEndereco(clienteEntity.getEndereco());
        verifyNoMoreInteractions(enderecoMapper);
    }

    @Test
    void deveMapearClienteParaClienteDtoResponse() {
        // Arrange
        when(enderecoMapper.toEnderecoDtoResponse(cliente.getEndereco())).thenReturn(enderecoDtoResponse);

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
        verify(enderecoMapper).toEnderecoDtoResponse(cliente.getEndereco());
        verifyNoMoreInteractions(enderecoMapper);
    }

    @Test
    void deveMapearClienteEntityParaClienteDtoResponse() {
        // Arrange
        when(enderecoMapper.toEnderecoDtoResponse(clienteEntity.getEndereco())).thenReturn(enderecoDtoResponse);

        // Act
        var result = clienteMapper.toResponse(clienteEntity);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.usuarioId()).isEqualTo(clienteEntity.getUsuarioId());
        assertThat(result.nome()).isEqualTo(clienteEntity.getNome());
        assertThat(result.email()).isEqualTo(clienteEntity.getEmail());
        assertThat(result.login()).isEqualTo(clienteEntity.getLogin());
        assertThat(result.senha()).isEqualTo(clienteEntity.getSenha());
        assertThat(result.dataHoraCriacao()).isEqualTo(clienteEntity.getDataHoraCriacao());
        assertThat(result.dataHoraEdicao()).isEqualTo(clienteEntity.getDataHoraEdicao());
        assertThat(result.endereco()).isEqualTo(enderecoDtoResponse);
        assertThat(result.numeroCartaoFidelidade()).isEqualTo(clienteEntity.getNumeroCartaoFidelidade());
        verify(enderecoMapper).toEnderecoDtoResponse(clienteEntity.getEndereco());
        verifyNoMoreInteractions(enderecoMapper);
    }

    @Test
    void deveMapearPageClienteEntityParaPageClienteDtoResponse() {
        // Arrange
        when(enderecoMapper.toEnderecoDtoResponse(clienteEntity.getEndereco())).thenReturn(enderecoDtoResponse);
        var page = new PageImpl<>(List.of(clienteEntity), PageRequest.of(0, 10), 1);

        // Act
        var result = clienteMapper.toPageResponse(page);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);
        var resultDto = result.getContent().getFirst();
        assertThat(resultDto.usuarioId()).isEqualTo(clienteEntity.getUsuarioId());
        assertThat(resultDto.nome()).isEqualTo(clienteEntity.getNome());
        assertThat(resultDto.email()).isEqualTo(clienteEntity.getEmail());
        assertThat(resultDto.login()).isEqualTo(clienteEntity.getLogin());
        assertThat(resultDto.senha()).isEqualTo(clienteEntity.getSenha());
        assertThat(resultDto.dataHoraCriacao()).isEqualTo(clienteEntity.getDataHoraCriacao());
        assertThat(resultDto.dataHoraEdicao()).isEqualTo(clienteEntity.getDataHoraEdicao());
        assertThat(resultDto.endereco()).isEqualTo(enderecoDtoResponse);
        assertThat(resultDto.numeroCartaoFidelidade()).isEqualTo(clienteEntity.getNumeroCartaoFidelidade());
        verify(enderecoMapper).toEnderecoDtoResponse(clienteEntity.getEndereco());
        verifyNoMoreInteractions(enderecoMapper);
    }
}

