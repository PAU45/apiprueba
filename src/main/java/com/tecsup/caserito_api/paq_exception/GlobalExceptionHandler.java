package com.tecsup.caserito_api.paq_exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Maneja las excepciones de validación de argumentos (cuando los datos no son válidos)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Recorrer los errores de validación y agregarlos al mapa
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Maneja la excepción personalizada de restaurante ya existente
    @ExceptionHandler(RestauranteExistenteException.class)
    public ResponseEntity<Map<String, String>> handleRestauranteExistenteException(RestauranteExistenteException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("msg", ex.getMessage());  // Enviar el mensaje de error de la excepción
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
