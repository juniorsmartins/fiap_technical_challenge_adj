package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.application.mapper.OutputMapper;
import br.com.fiap.tech.challenge_user.application.port.out.FindByIdOutputPort;
import br.com.fiap.tech.challenge_user.domain.exception.http404.RecursoNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Tag(name = "FindById/Get", description = "Contém recurso de consultar por Id.")
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractFindController<O, T, E> {

    private final OutputMapper<T, O, E> outputMapper;

    private final FindByIdOutputPort<E> findByIdOutputPort;

    @GetMapping(path = {"/{id}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Consultar", description = "Buscar um recurso por id.",
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
    public ResponseEntity<O> findById(
            @Parameter(name = "id", description = "Identificador único do recurso.",
                    example = "034eb74c-69ee-4bd4-a064-5c4cc5e9e748", required = true)
            @PathVariable(name = "id") final UUID id) {

        var response = findByIdOutputPort.findById(id)
                .map(outputMapper::toResponse)
                .orElseThrow(() -> {
                    log.error("AbstractFindController - Recurso não encontrado por id: {}.", id);
                    return new RecursoNotFoundException(id);
                });

        return ResponseEntity
                .ok()
                .body(response);
    }
}

