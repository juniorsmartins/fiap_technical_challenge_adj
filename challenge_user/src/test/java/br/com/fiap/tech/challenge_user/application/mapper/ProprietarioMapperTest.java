package br.com.fiap.tech.challenge_user.application.mapper;

import br.com.fiap.tech.challenge_user.domain.entities.Endereco;
import br.com.fiap.tech.challenge_user.domain.entities.Proprietario;
import br.com.fiap.tech.challenge_user.application.dtos.in.EnderecoDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.in.ProprietarioDtoRequest;
import br.com.fiap.tech.challenge_user.application.dtos.out.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.EnderecoDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ProprietarioDao;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.EnderecoPresenter;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.ProprietarioPresenter;
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
class ProprietarioMapperTest {

    @Mock
    private EnderecoPresenter enderecoPresenter;

    @InjectMocks
    private ProprietarioPresenter proprietarioMapper;

    private ProprietarioDtoRequest proprietarioDtoRequest;

    private Proprietario proprietario;

    private ProprietarioDao proprietarioEntity;

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

        proprietarioDtoRequest = new ProprietarioDtoRequest(
                "João Silva", "joao@email.com", "jsilva", "jsilva!123",
                enderecoDtoRequest, "Proprietário principal"
        );

        endereco = new Endereco();
        endereco.setEnderecoId(enderecoId);
        endereco.setCep("01001-000");
        endereco.setLogradouro("Avenida Central");
        endereco.setNumero("1500");

        proprietario = new Proprietario(
                usuarioId, "João Silva", "joao@email.com", "jsilva", "jsilva!123",
                endereco, "Proprietário principal"
        );

        var enderecoEntity = new EnderecoDao(
                enderecoId, "01001-000", "Avenida Central", "1500"
        );

        proprietarioEntity = new ProprietarioDao(
                usuarioId, "João Silva", "joao@email.com", "jsilva", "jsilva!123",
                enderecoEntity, "Proprietário principal", dataHoraCriacao, dataHoraEdicao
        );

        enderecoDtoResponse = new EnderecoDtoResponse(
                enderecoId, "01001-000", "Avenida Central", "1500"
        );
    }

    @Test
    void deveMapearProprietarioDtoRequestParaProprietario() {
        // Arrange
        when(enderecoPresenter.toEndereco(proprietarioDtoRequest.endereco())).thenReturn(endereco);

        // Act
        var result = proprietarioMapper.toDomainIn(proprietarioDtoRequest);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUsuarioId()).isNull();
        assertThat(result.getNome()).isEqualTo(proprietarioDtoRequest.nome());
        assertThat(result.getEmail()).isEqualTo(proprietarioDtoRequest.email());
        assertThat(result.getLogin()).isEqualTo(proprietarioDtoRequest.login());
        assertThat(result.getSenha()).isEqualTo(proprietarioDtoRequest.senha());
        assertThat(result.getEndereco()).isEqualTo(endereco);
        assertThat(result.getDescricao()).isEqualTo(proprietarioDtoRequest.descricao());
        verify(enderecoPresenter).toEndereco(proprietarioDtoRequest.endereco());
        verifyNoMoreInteractions(enderecoPresenter);
    }

    @Test
    void deveMapearProprietarioParaProprietarioEntity() {
        // Arrange
        when(enderecoPresenter.toEnderecoEntity(proprietario.getEndereco())).thenReturn(proprietarioEntity.getEndereco());

        // Act
        var result = proprietarioMapper.toEntity(proprietario);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUsuarioId()).isEqualTo(proprietario.getUsuarioId());
        assertThat(result.getNome()).isEqualTo(proprietario.getNome());
        assertThat(result.getEmail()).isEqualTo(proprietario.getEmail());
        assertThat(result.getLogin()).isEqualTo(proprietario.getLogin());
        assertThat(result.getSenha()).isEqualTo(proprietario.getSenha());
        assertThat(result.getEndereco()).isEqualTo(proprietarioEntity.getEndereco());
        assertThat(result.getDescricao()).isEqualTo(proprietario.getDescricao());
        assertThat(result.getDataHoraCriacao()).isNull();
        assertThat(result.getDataHoraEdicao()).isNull();
        verify(enderecoPresenter).toEnderecoEntity(proprietario.getEndereco());
        verifyNoMoreInteractions(enderecoPresenter);
    }

    @Test
    void deveMapearProprietarioEntityParaProprietario() {
        // Arrange
        when(enderecoPresenter.toEndereco(proprietarioEntity.getEndereco())).thenReturn(endereco);

        // Act
        var result = proprietarioMapper.toDomain(proprietarioEntity);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUsuarioId()).isEqualTo(proprietarioEntity.getUsuarioId());
        assertThat(result.getNome()).isEqualTo(proprietarioEntity.getNome());
        assertThat(result.getEmail()).isEqualTo(proprietarioEntity.getEmail());
        assertThat(result.getLogin()).isEqualTo(proprietarioEntity.getLogin());
        assertThat(result.getSenha()).isEqualTo(proprietarioEntity.getSenha());
        assertThat(result.getEndereco()).isEqualTo(endereco);
        assertThat(result.getDescricao()).isEqualTo(proprietarioEntity.getDescricao());
        verify(enderecoPresenter).toEndereco(proprietarioEntity.getEndereco());
        verifyNoMoreInteractions(enderecoPresenter);
    }

    @Test
    void deveMapearProprietarioParaProprietarioDtoResponse() {
        // Arrange
        when(enderecoPresenter.toEnderecoDtoResponse(proprietario.getEndereco())).thenReturn(enderecoDtoResponse);

        // Act
        var result = proprietarioMapper.toDtoResponse(proprietario);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.usuarioId()).isEqualTo(proprietario.getUsuarioId());
        assertThat(result.nome()).isEqualTo(proprietario.getNome());
        assertThat(result.email()).isEqualTo(proprietario.getEmail());
        assertThat(result.login()).isEqualTo(proprietario.getLogin());
        assertThat(result.senha()).isEqualTo(proprietario.getSenha());
        assertThat(result.endereco()).isEqualTo(enderecoDtoResponse);
        assertThat(result.descricao()).isEqualTo(proprietario.getDescricao());
        assertThat(result.dataHoraCriacao()).isNull();
        assertThat(result.dataHoraEdicao()).isNull();
        verify(enderecoPresenter).toEnderecoDtoResponse(proprietario.getEndereco());
        verifyNoMoreInteractions(enderecoPresenter);
    }

    @Test
    void deveMapearProprietarioEntityParaProprietarioDtoResponse() {
        // Arrange
        when(enderecoPresenter.toEnderecoDtoResponse(proprietarioEntity.getEndereco())).thenReturn(enderecoDtoResponse);

        // Act
        var result = proprietarioMapper.toResponse(proprietarioEntity);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.usuarioId()).isEqualTo(proprietarioEntity.getUsuarioId());
        assertThat(result.nome()).isEqualTo(proprietarioEntity.getNome());
        assertThat(result.email()).isEqualTo(proprietarioEntity.getEmail());
        assertThat(result.login()).isEqualTo(proprietarioEntity.getLogin());
        assertThat(result.senha()).isEqualTo(proprietarioEntity.getSenha());
        assertThat(result.dataHoraCriacao()).isEqualTo(proprietarioEntity.getDataHoraCriacao());
        assertThat(result.dataHoraEdicao()).isEqualTo(proprietarioEntity.getDataHoraEdicao());
        assertThat(result.endereco()).isEqualTo(enderecoDtoResponse);
        assertThat(result.descricao()).isEqualTo(proprietarioEntity.getDescricao());
        verify(enderecoPresenter).toEnderecoDtoResponse(proprietarioEntity.getEndereco());
        verifyNoMoreInteractions(enderecoPresenter);
    }

    @Test
    void deveMapearPageProprietarioEntityParaPageProprietarioDtoResponse() {
        // Arrange
        when(enderecoPresenter.toEnderecoDtoResponse(proprietarioEntity.getEndereco())).thenReturn(enderecoDtoResponse);
        var page = new PageImpl<>(List.of(proprietarioEntity), PageRequest.of(0, 10), 1);

        // Act
        var result = proprietarioMapper.toPageResponse(page);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);
        var resultDto = result.getContent().getFirst();
        assertThat(resultDto.usuarioId()).isEqualTo(proprietarioEntity.getUsuarioId());
        assertThat(resultDto.nome()).isEqualTo(proprietarioEntity.getNome());
        assertThat(resultDto.email()).isEqualTo(proprietarioEntity.getEmail());
        assertThat(resultDto.login()).isEqualTo(proprietarioEntity.getLogin());
        assertThat(resultDto.senha()).isEqualTo(proprietarioEntity.getSenha());
        assertThat(resultDto.dataHoraCriacao()).isEqualTo(proprietarioEntity.getDataHoraCriacao());
        assertThat(resultDto.dataHoraEdicao()).isEqualTo(proprietarioEntity.getDataHoraEdicao());
        assertThat(resultDto.endereco()).isEqualTo(enderecoDtoResponse);
        assertThat(resultDto.descricao()).isEqualTo(proprietarioEntity.getDescricao());
        verify(enderecoPresenter).toEnderecoDtoResponse(proprietarioEntity.getEndereco());
        verifyNoMoreInteractions(enderecoPresenter);
    }
}

