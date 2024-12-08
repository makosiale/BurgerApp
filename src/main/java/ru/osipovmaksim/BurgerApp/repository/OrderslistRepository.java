package ru.osipovmaksim.BurgerApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.osipovmaksim.BurgerApp.entity.Orderslist;

public interface OrderslistRepository extends JpaRepository<Orderslist, Long> {
}