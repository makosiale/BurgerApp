package ru.osipovmaksim.BurgerApp.dto;

import java.io.Serializable;

/**
 * DTO for {@link ru.osipovmaksim.BurgerApp.entity.Employee}
 */
public record EmployeeDto(String name, String surname, String patronymic, String position,
                          String phoneNumber) implements Serializable {
}