package ru.osipovmaksim.BurgerApp.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link ru.osipovmaksim.BurgerApp.entity.Order}
 */
public record OrderDto(Instant datetime, String adress, EmployeeDto employee) implements Serializable {
}