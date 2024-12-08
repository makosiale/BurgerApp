package ru.osipovmaksim.BurgerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.osipovmaksim.BurgerApp.dto.UserDto;
import ru.osipovmaksim.BurgerApp.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Override
    Optional<User> findById(Integer integer);
}