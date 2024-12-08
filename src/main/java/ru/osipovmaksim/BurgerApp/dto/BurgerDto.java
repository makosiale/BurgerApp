package ru.osipovmaksim.BurgerApp.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link ru.osipovmaksim.BurgerApp.entity.Burger}
 */
public record BurgerDto(String name, BigDecimal cost) implements Serializable {
}