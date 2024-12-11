package ru.osipovmaksim.BurgerApp.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IngredientAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handlerIngredientAlreadyExistsException(IngredientAlreadyExistsException ex) {
        Map<String, Object> errorResponce = new HashMap<>();
        errorResponce.put("status", HttpStatus.CONFLICT.value());
        errorResponce.put("error", "Conflict");
        errorResponce.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponce);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handlerGeneralException(Exception ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.put("error", "Internal Server Error");
        errorResponse.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handlerUserAlreadyExistsException(UserAlreadyExistsException ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", HttpStatus.CONFLICT.value());
        map.put("error", "Conflict");
        map.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(map);
    }
}
