package br.com.fiap.tech.challenge_user.infrastructure.adapter.in;

import br.com.fiap.tech.challenge_user.domain.exception.http500.InternalServerProblemException;
import br.com.fiap.tech.challenge_user.application.port.in.DeleteByIdInputPort;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.UUID;

@Tag(name = "DeleteById/Delete", description = "Contém recurso de deletar.")
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractDeleteController<T> {

    private final DeleteByIdInputPort<T> deleteByIdInputPort;

    @DeleteMapping(path = {"/{id}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "DeleteById", description = "Apagar recurso do banco de dados.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content - requisição bem sucedida e sem retorno.",
                            content = {@Content(mediaType = "application/json")}
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request - requisição mal formulada.",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDetail.class))}
                    ),
                    @ApiResponse(responseCode = "404", description = "Not Found - recurso não encontrado no banco de dados.",
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
    public ResponseEntity<Void> deleteById(
            @Parameter(name = "id", description = "Identificador único do recurso.",
                    example = "034eb74c-69ee-4bd4-a064-5c4cc5e9e748", required = true)
            @PathVariable(name = "id") final UUID id
    ) {

        Optional.ofNullable(id)
                .ifPresentOrElse(deleteByIdInputPort::deleteById, () -> {
                    log.error("AbstractDeleteController - Erro interno do servidor no método deleteById.");
                    throw new InternalServerProblemException();
                });

        return ResponseEntity
                .noContent()
                .build();
    }
}

