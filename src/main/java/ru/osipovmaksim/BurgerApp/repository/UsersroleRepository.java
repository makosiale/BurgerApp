package ru.osipovmaksim.BurgerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.osipovmaksim.BurgerApp.entity.Role;
import ru.osipovmaksim.BurgerApp.entity.User;
import ru.osipovmaksim.BurgerApp.entity.Usersrole;

import java.util.Set;

public interface UsersroleRepository extends JpaRepository<Usersrole, Long> {

    Set<Usersrole> findByUser(User user);
}