package br.com.api.petpoints.shared.exception;

import br.com.api.petpoints.domain.auth.exception.UsuarioNaoEncontrado;
import br.com.api.petpoints.shared.exception.custom.IllegalAccessException;
import br.com.api.petpoints.shared.exception.custom.NegocioException;
import br.com.api.petpoints.shared.exception.custom.ObjectNotFoundException;
import br.com.api.petpoints.shared.exception.custom.PerfilDesativadoException;
import br.com.api.petpoints.shared.exception.custom.StandardException;
import br.com.api.petpoints.shared.exception.custom.TokenExpiradaException;
import br.com.api.petpoints.shared.features.logs.LogsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionGlobalHandler {

    private final LogsServiceImpl logsService;

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<StandardException> handleNegocio(NegocioException ex, HttpServletRequest request) {
        HttpStatus status = ex.getStatus();
        StandardException error = new StandardException(
                LocalDateTime.now(),
                status.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        logsService.registrarException(ex, request, status);
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(TokenExpiradaException.class)
    public ResponseEntity<StandardException> handleTokenExpirada(TokenExpiradaException ex, HttpServletRequest request) {
        StandardException error = new StandardException(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        logsService.registrarException(ex, request, HttpStatus.UNAUTHORIZED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(UsuarioNaoEncontrado.class)
    public ResponseEntity<StandardException> handleUsuarioNaoEncontrado(UsuarioNaoEncontrado ex, HttpServletRequest request) {
        StandardException error = new StandardException(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        logsService.registrarException(ex, request, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardException> handleObjectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {
        StandardException error = new StandardException(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        logsService.registrarException(ex, request, HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(PerfilDesativadoException.class)
    public ResponseEntity<StandardException> handlePerfilDesativado(PerfilDesativadoException ex, HttpServletRequest request) {
        StandardException error = new StandardException(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        logsService.registrarException(ex, request, HttpStatus.FORBIDDEN);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<StandardException> handleIllegalAccess(IllegalAccessException ex, HttpServletRequest request) {
        StandardException error = new StandardException(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        logsService.registrarException(ex, request, HttpStatus.FORBIDDEN);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardException> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String mensagem = ex.getBindingResult().getFieldErrors().stream()
                .map(field -> field.getField() + ": " + field.getDefaultMessage())
                .collect(Collectors.joining("; "));
        StandardException error = new StandardException(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                mensagem,
                request.getRequestURI()
        );
        logsService.registrarException(ex, request, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(java.lang.IllegalArgumentException.class)
    public ResponseEntity<StandardException> handleIllegalArgument(java.lang.IllegalArgumentException ex, HttpServletRequest request) {
        StandardException error = new StandardException(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getRequestURI()
        );
        logsService.registrarException(ex, request, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardException> handleGeneric(Exception ex, HttpServletRequest request) {
        log.error("Erro inesperado: {}", ex.getMessage(), ex);
        logsService.registrarException(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
        StandardException error = new StandardException(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno do servidor. Tente novamente mais tarde.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
