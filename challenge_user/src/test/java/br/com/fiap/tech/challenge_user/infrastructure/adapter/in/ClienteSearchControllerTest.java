package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.infrastructure.adapters.controllers.ClienteSearchController;
import br.com.fiap.tech.challenge_user.infrastructure.adapters.presenters.PagePresenter;
import br.com.fiap.tech.challenge_user.application.interfaces.out.UsuarioSearchOutputPort;
import br.com.fiap.tech.challenge_user.application.dtos.filters.UsuarioFiltroDto;
import br.com.fiap.tech.challenge_user.application.dtos.out.ClienteDtoResponse;
import br.com.fiap.tech.challenge_user.application.dtos.out.EnderecoDtoResponse;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.ClienteDao;
import br.com.fiap.tech.challenge_user.infrastructure.drivers.daos.EnderecoDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteSearchControllerTest {

    @Mock
    private PagePresenter<ClienteDtoResponse, ClienteDao> mapper;

    @Mock
    private UsuarioSearchOutputPort<ClienteDao> finder;

    @InjectMocks
    private ClienteSearchController clienteSearchController;

    private UsuarioFiltroDto filtroDto;

    private Pageable paginacao;

    private Page<ClienteDao> clienteEntityPage;

    private Page<ClienteDtoResponse> clienteDtoResponsePage;

    @BeforeEach
    void setUp() {
        var clienteId = UUID.randomUUID();
        var enderecoId = UUID.randomUUID();

        filtroDto = new UsuarioFiltroDto(
                null, "Robert Martin", null, null, null);

        paginacao = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "usuarioId"));

        var enderecoEntity = new EnderecoDao();
        enderecoEntity.setCep("01001-000");
        enderecoEntity.setLogradouro("Avenida Central");
        enderecoEntity.setNumero("1500");

        var clienteEntity = new ClienteDao();
        clienteEntity.setUsuarioId(clienteId);
        clienteEntity.setNome("Robert Martin");
        clienteEntity.setEmail("martin@email.com");
        clienteEntity.setLogin("rmartin");
        clienteEntity.setSenha("rmartin!123");
        clienteEntity.setDataHoraCriacao(Date.from(Instant.now()));
        clienteEntity.setNumeroCartaoFidelidade("12345-6789-3245");
        clienteEntity.setEndereco(enderecoEntity);

        clienteEntityPage = new PageImpl<>(Collections.singletonList(clienteEntity), paginacao, 1);

        var enderecoDtoResponse = new EnderecoDtoResponse(
                enderecoId, "01001-000", "Avenida Central", "1500"
        );

        var clienteDtoResponse = new ClienteDtoResponse(
                clienteId, "Robert Martin", "martin@email.com", "rmartin", "rmartin!123",
                Date.from(Instant.now()), null, enderecoDtoResponse, "12345-6789-3245"
        );

        clienteDtoResponsePage = new PageImpl<>(Collections.singletonList(clienteDtoResponse), paginacao, 1);
    }

    @Test
    void devePesquisarClienteComSucesso() {
        // Arrange
        doReturn(clienteEntityPage).when(finder).search(filtroDto, paginacao);
        doReturn(clienteDtoResponsePage).when(mapper).toPageResponse(clienteEntityPage);

        // Act
        ResponseEntity<Page<ClienteDtoResponse>> response = clienteSearchController.search(filtroDto, paginacao);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clienteDtoResponsePage, response.getBody());
        verify(finder, times(1)).search(filtroDto, paginacao);
        verify(mapper, times(1)).toPageResponse(clienteEntityPage);
        verifyNoMoreInteractions(finder, mapper);
    }

    @Test
    void deveLancarNoSuchElementExceptionQuandoMapperRetornaNulo() {
        // Arrange
        doReturn(clienteEntityPage).when(finder).search(filtroDto, paginacao);
        doReturn(null).when(mapper).toPageResponse(clienteEntityPage);

        // Act & Assert
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> clienteSearchController.search(filtroDto, paginacao)
        );

        assertEquals("No value present", exception.getMessage());
        verify(finder, times(1)).search(filtroDto, paginacao);
        verify(mapper, times(1)).toPageResponse(clienteEntityPage);
        verifyNoMoreInteractions(finder, mapper);
    }
}

