package br.com.starterpack.exception;

import br.com.starterpack.core.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({
            BusinessException.class,
            ModelNotFoundException.class
    })
    private ResponseEntity<ErrorResponse> buildBusinessException(Exception ex, WebRequest request) {
        HttpStatus statusCode = ex instanceof ModelNotFoundException ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;

        ErrorResponse error = new ErrorResponse();
        error.setError(ex.getMessage());
        error.setStatus(statusCode.value());

        return new ResponseEntity<>(error, statusCode);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<ErrorResponse> badCredentialsException(BadCredentialsException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setError("Usuário ou senha incorretos");
        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorResponse> buildUnknownException(Exception ex) {
        log.error("buildUnknownException", ex);
        ErrorResponse error = new ErrorResponse();
        error.setError("Ocorreu um erro interno");
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    private ResponseEntity<ErrorResponse> duplicateKeyException(DuplicateKeyException ex) {
        ErrorResponse error = new ErrorResponse();
        error.setError("Registro já existe");
        error.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }
}