package ru.osipovmaksim.BurgerApp.dto;

import java.io.Serializable;

/**
 * DTO for {@link ru.osipovmaksim.BurgerApp.entity.Recept}
 */
public record ReceptDto(BurgerDto burger, IngredientDto ingredient, Integer quantity) implements Serializable {
}