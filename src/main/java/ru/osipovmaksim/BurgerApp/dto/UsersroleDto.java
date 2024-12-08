package ru.osipovmaksim.BurgerApp.dto;

import java.io.Serializable;

/**
 * DTO for {@link ru.osipovmaksim.BurgerApp.entity.Usersrole}
 */
public record UsersroleDto(UserDto user, RoleDto role) implements Serializable {
}