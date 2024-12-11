package ru.osipovmaksim.BurgerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.osipovmaksim.BurgerApp.entity.Burger;

import java.util.Optional;

public interface BurgerRepository extends JpaRepository<Burger, Long> {

    Optional<Burger> findById(int id);
}
