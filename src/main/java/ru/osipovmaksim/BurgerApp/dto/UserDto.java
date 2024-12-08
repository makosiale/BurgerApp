package ru.osipovmaksim.BurgerApp.dto;

import java.io.Serializable;

/**
 * DTO for {@link ru.osipovmaksim.BurgerApp.entity.User}
 */
public record UserDto(String username, String password, String name, String telephone) implements Serializable {
}