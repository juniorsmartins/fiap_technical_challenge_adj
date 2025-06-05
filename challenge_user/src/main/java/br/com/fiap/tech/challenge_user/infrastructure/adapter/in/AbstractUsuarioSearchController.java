package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.application.mapper.OutputMapper;
import br.com.fiap.tech.challenge_user.application.port.out.UsuarioSearchOutputPort;
import br.com.fiap.tech.challenge_user.infrastructure.dto.filter.UsuarioFiltroDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Tag(name = "Usuários", description = "Contém recurso para pesquisar.")
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractUsuarioSearchController<O, E> {

    private final OutputMapper<?, O, E> mapper;

    private final UsuarioSearchOutputPort<E> outputPort;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Pesquisar", description = "Buscar um recurso por valores de atributos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK - requisição bem sucedida e com retorno.",
                            content = {@Content(mediaType = "application/json")}
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request - requisição mal formulada.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class))}
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error - situação inesperada no servidor.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class))}
                    )
            }
    )
    public ResponseEntity<Page<O>> search(
            @Parameter(name = "FiltroDto", description = "Estrutura de dados para filtrar pesquisa.", required = false)
            @Valid final UsuarioFiltroDto filtroDto,
            @PageableDefault(sort = "usuarioId", direction = Sort.Direction.DESC, page = 0, size = 10) final Pageable paginacao) {

        var response = Optional.of(filtroDto)
                .map(filtro -> outputPort.search(filtro, paginacao))
                .map(mapper::toPageResponse)
                .orElseThrow();

        return ResponseEntity
                .ok()
                .body(response);
    }
}

