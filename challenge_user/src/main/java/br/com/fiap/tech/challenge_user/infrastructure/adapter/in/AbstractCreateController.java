package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.application.exception.http500.InternalServerProblemException;
import br.com.fiap.tech.challenge_user.infrastructure.presenters.InputMapper;
import br.com.fiap.tech.challenge_user.infrastructure.presenters.OutputMapper;
import br.com.fiap.tech.challenge_user.application.port.in.CreateInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Tag(name = "Create/Post", description = "Contém recurso de cadastrar.")
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractCreateController<I, O, T, E> {

    private final InputMapper<I, T> inputMapper;

    private final OutputMapper<T, O, E> outputMapper;

    private final CreateInputPort<T> createInputPort;

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(summary = "Cadastrar", description = "Criar um novo recurso.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created - recurso cadastrado com sucesso.",
                            content = {@Content(mediaType = "application/json")}
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request - requisição mal formulada.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class))}
                    ),
                    @ApiResponse(responseCode = "404", description = "Not Found - recurso não encontrado.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class))}
                    ),
                    @ApiResponse(responseCode = "409", description = "Conflict - violação de regras de negócio.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class))}
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error - situação inesperada no servidor.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class))}
                    )
            }
    )
    public ResponseEntity<O> create(
            @Parameter(name = "DtoRequest", description = "Para transporte de dados de entrada.", required = true)
            @RequestBody @Valid I dtoRequest) {

        var response = Optional.ofNullable(dtoRequest)
                .map(inputMapper::toDomainIn)
                .map(createInputPort::create)
                .map(outputMapper::toDtoResponse)
                .orElseThrow(() -> {
                    log.error("AbstractCreateController - Erro interno do servidor no método create.");
                    return new InternalServerProblemException();
                });

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}

