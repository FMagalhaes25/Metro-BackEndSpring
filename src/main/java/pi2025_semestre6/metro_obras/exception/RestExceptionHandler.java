package pi2025_semestre6.metro_obras.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class RestExceptionHandler {

    // Handler para falhas de login (senha errada)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException ex, WebRequest request) {
        String message = "E-mail ou senha inválidos.";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }

    // Handler para falhas de login (usuário não encontrado)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFound(UsernameNotFoundException ex, WebRequest request) {
        String message = "E-mail ou senha inválidos."; // Mesma mensagem por segurança
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }

    // Handler genérico para outros erros inesperados (Erros 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest request) {
        // Logar o erro real no console para depuração
        ex.printStackTrace();

        String message = "Ocorreu um erro interno no servidor.";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }
}