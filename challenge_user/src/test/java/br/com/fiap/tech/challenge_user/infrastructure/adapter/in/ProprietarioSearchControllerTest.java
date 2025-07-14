package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.presenters.PageMapper;
import br.com.fiap.tech.challenge_user.application.interfaces.out.UsuarioSearchOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.dto.filter.UsuarioFiltroDto;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.dto.out.ProprietarioDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.entity.EnderecoEntity;
import br.com.fiap.tech.challenge_user.infrastructure.entity.ProprietarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProprietarioSearchControllerTest {

    @Mock
    private PageMapper<ProprietarioDtoResponse, ProprietarioEntity> mapper;

    @Mock
    private UsuarioSearchOutputPort<ProprietarioEntity> finder;

    @InjectMocks
    private ProprietarioSearchController proprietarioSearchController;

    private UsuarioFiltroDto filtroDto;

    private Pageable paginacao;

    private Page<ProprietarioEntity> proprietarioEntityPage;

    private Page<ProprietarioDtoResponse> proprietarioDtoResponsePage;

    @BeforeEach
    void setUp() {
        var proprietarioId = UUID.randomUUID();
        var enderecoId = UUID.randomUUID();

        filtroDto = new UsuarioFiltroDto(
                null, "João Silva", null, null, null);

        paginacao = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "usuarioId"));

        var enderecoEntity = new EnderecoEntity();
        enderecoEntity.setCep("01001-000");
        enderecoEntity.setLogradouro("Avenida Central");
        enderecoEntity.setNumero("1500");

        var proprietarioEntity = new ProprietarioEntity();
        proprietarioEntity.setUsuarioId(proprietarioId);
        proprietarioEntity.setNome("João Silva");
        proprietarioEntity.setEmail("joao@email.com");
        proprietarioEntity.setLogin("jsilva");
        proprietarioEntity.setSenha("jsilva!123");
        proprietarioEntity.setEndereco(enderecoEntity);
        proprietarioEntity.setDescricao("Proprietário principal");

        proprietarioEntityPage = new PageImpl<>(Collections.singletonList(proprietarioEntity), paginacao, 1);

        var enderecoDtoResponse = new EnderecoDtoResponse(
                enderecoId, "01001-000", "Avenida Central", "1500"
        );

        var proprietarioDtoResponse = new ProprietarioDtoResponse(
                proprietarioId, "João Silva", "joao@email.com", "jsilva", "jsilva!123",
                null, null, enderecoDtoResponse, "Proprietário principal"
        );

        proprietarioDtoResponsePage = new PageImpl<>(
                Collections.singletonList(proprietarioDtoResponse), paginacao, 1);
    }

    @Test
    void devePesquisarProprietarioComSucesso() {
        // Arrange
        doReturn(proprietarioEntityPage).when(finder).search(filtroDto, paginacao);
        doReturn(proprietarioDtoResponsePage).when(mapper).toPageResponse(proprietarioEntityPage);

        // Act
        ResponseEntity<Page<ProprietarioDtoResponse>> response = proprietarioSearchController.search(filtroDto, paginacao);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(proprietarioDtoResponsePage, response.getBody());
        verify(finder, times(1)).search(filtroDto, paginacao);
        verify(mapper, times(1)).toPageResponse(proprietarioEntityPage);
        verifyNoMoreInteractions(finder, mapper);
    }

    @Test
    void deveLancarNoSuchElementExceptionQuandoMapperRetornaNulo() {
        // Arrange
        doReturn(proprietarioEntityPage).when(finder).search(filtroDto, paginacao);
        doReturn(null).when(mapper).toPageResponse(proprietarioEntityPage);

        // Act & Assert
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> proprietarioSearchController.search(filtroDto, paginacao)
        );

        assertEquals("No value present", exception.getMessage());
        verify(finder, times(1)).search(filtroDto, paginacao);
        verify(mapper, times(1)).toPageResponse(proprietarioEntityPage);
        verifyNoMoreInteractions(finder, mapper);
    }
}

