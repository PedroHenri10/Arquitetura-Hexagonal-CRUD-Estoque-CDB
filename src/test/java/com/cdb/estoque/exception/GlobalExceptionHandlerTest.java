package com.cdb.estoque.exception;

import com.cdb.estoque.adapter.input.response.ErroResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private HttpServletRequest request;

    @BeforeEach
    void setup() {
        handler = new GlobalExceptionHandler();
        request = mock(HttpServletRequest.class);
        Mockito.when(request.getRequestURI()).thenReturn("/games/1");
    }

    @Test
    void deveTratarResourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Jogo não encontrado");

        ResponseEntity<ErroResponse> response = handler.handleNotFound(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getMessage()).isEqualTo("Jogo não encontrado");
        assertThat(response.getBody().getPath()).isEqualTo("/games/1");
    }

    @Test
    void deveTratarIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("ID inválido");

        ResponseEntity<ErroResponse> response = handler.handleIllegalArgument(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo("ID inválido");
        assertThat(response.getBody().getError()).isEqualTo("Bad Request");
    }

    @Test
    void deveTratarMethodArgumentNotValidException() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("obj", "nome", "não pode ser vazio");
        Mockito.when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Map<String, String>> response = handler.handleValidation(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsEntry("nome", "não pode ser vazio");
    }
}
