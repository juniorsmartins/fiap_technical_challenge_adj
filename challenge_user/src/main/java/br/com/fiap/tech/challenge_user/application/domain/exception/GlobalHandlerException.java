package br.com.fiap.tech.challenge_user.application.domain.exception;

import br.com.fiap.tech.challenge_user.application.domain.exception.http404.ResourceNotFoundException;
import br.com.fiap.tech.challenge_user.application.domain.exception.http409.RegraDeNegocioVioladaException;
import br.com.fiap.tech.challenge_user.application.domain.exception.http500.InternalServerProblemException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public final class GlobalHandlerException extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    // ---------- TRATAMENTO DE EXCEÇÕES CUSTOMIZADAS ---------- //
    // ---------- 404 Not Found ---------- //
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFound(ResourceNotFoundException ex, WebRequest webRequest) {

        log.info("class = GlobalHandlerException e método = handleResourceNotFound", ex);

        var id = ex.getId();

        var httpStatus = HttpStatus.NOT_FOUND;
        var mensagem = this.messageSource
                .getMessage(ex.getMessageKey(), new Object[]{id.toString()}, LocaleContextHolder.getLocale());

        var problemDetail = createProblemDetail(
                httpStatus,
                "https://paginax.com/erros/recurso-nao-encontrado",
                mensagem,
                null);

        return ResponseEntity
                .status(httpStatus)
                .body(problemDetail);
    }

    // ---------- 409 Conflict ---------- //
    @ExceptionHandler(RegraDeNegocioVioladaException.class)
    public ResponseEntity<ProblemDetail> handleRegraViolada(RegraDeNegocioVioladaException ex) {

        log.info("class = GlobalHandlerException e método = handleRegraViolada", ex);

        var email = ex.getEmail();

        var httpStatus = HttpStatus.CONFLICT;
        var mensagem = this.messageSource
                .getMessage(ex.getMessageKey(), new Object[]{email}, LocaleContextHolder.getLocale());

        var problemDetail = createProblemDetail(
                httpStatus,
                "https://paginax.com/erros/regra-em-conflito",
                mensagem,
                null);

        return ResponseEntity
                .status(httpStatus)
                .body(problemDetail);
    }

    // ---------- 500 Internal Server Error ---------- //
    @ExceptionHandler(InternalServerProblemException.class)
    public ResponseEntity<ProblemDetail> handleInternalServerProblem(InternalServerProblemException ex, WebRequest webRequest) {

        log.info("class = GlobalHandlerException e método = handleInternalServerProblem", ex);

        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var mensagem = this.getMessage("exception.internal.server.error");

        var problemDetail = createProblemDetail(
                httpStatus,
                "https://paginax.com/erros/erro-interno-servidor",
                mensagem,
                null);

        return ResponseEntity
                .status(httpStatus)
                .body(problemDetail);
    }

    // ---------- TRATAMENTO DE EXCEÇÕES DEFAULT ---------- //
    // ---------- Sobreescrever metodo de ResponseEntityExceptionHandler para customizar ---------- //
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders httpHeaders,
                                                                  HttpStatusCode httpStatusCode,
                                                                  WebRequest webRequest) {

        log.info("class = GlobalHandlerException e método = handleMethodArgumentNotValid", ex);

        var fields = this.getFields(ex);

        var mensagem = this.getMessage("exception.request.attribute.invalid");

        var problemDetail = createProblemDetail(
                httpStatusCode,
                "https://paginax.com/erros/campos-invalidos",
                mensagem,
                Map.of("fields", fields));

        return super.handleExceptionInternal(ex, problemDetail, httpHeaders, httpStatusCode, webRequest);
    }

    // ---------- MÉTODOS ÚTEIS ---------- //
    private List<Map<String, String>> getFields(BindException ex) {
        return ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    Map<String, String> errorDetails = new HashMap<>();

                    if (error instanceof FieldError fieldError) {
                        errorDetails.put("field", fieldError.getField());
                        errorDetails.put("rejectedValue", String.valueOf(fieldError.getRejectedValue()));
                    } else {
                        errorDetails.put("field", error.getObjectName());
                    }
                    errorDetails.put("message", this.messageSource.getMessage(error, LocaleContextHolder.getLocale()));

                    return errorDetails;
                })
                .toList();
    }

    private ProblemDetail createProblemDetail(HttpStatusCode status, String typeUri, String title, Map<String, Object> properties) {

        // ProblemDetail RFC 7807
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setType(URI.create(typeUri));
        problemDetail.setTitle(title);
        problemDetail.setProperty("errorId", UUID.randomUUID().toString());
        problemDetail.setProperty("timestamp", Instant.now().toString());

        if (properties != null) {
            properties.forEach(problemDetail::setProperty);
        }

        return problemDetail;
    }

    private String getMessage(final String messageKey) {
        return this.messageSource.getMessage(messageKey, new Object[]{}, LocaleContextHolder.getLocale());
    }

    // ---------- TRATAMENTO GERAL - acionado se nenhuma exceção específica capturar ---------- //
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception ex, WebRequest webRequest) {

        log.info("class = GlobalHandlerException e método = handleGenericException", ex);

        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var mensagem = this.getMessage("exception.internal.server.error");

        var problemDetail = createProblemDetail(
                httpStatus,
                "https://paginax.com/erros/erro-interno-servidor",
                mensagem,
                null);

        return ResponseEntity
                .status(httpStatus)
                .body(problemDetail);
    }
}

