package ru.osipovmaksim.BurgerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.osipovmaksim.BurgerApp.entity.Recept;

public interface ReceptRepository extends JpaRepository<Recept, Long> {
}