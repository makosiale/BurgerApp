package ru.osipovmaksim.BurgerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.osipovmaksim.BurgerApp.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}