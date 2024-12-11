package ru.osipovmaksim.BurgerApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class IngredientAlreadyExistsException extends RuntimeException {
    public IngredientAlreadyExistsException(String messsage) {
        super(messsage);
    }
}
