package ru.osipovmaksim.BurgerApp.dto;

import java.io.Serializable;

/**
 * DTO for {@link ru.osipovmaksim.BurgerApp.entity.Ingredient}
 */
public record IngredientDto(String name, Integer residue) implements Serializable {
}