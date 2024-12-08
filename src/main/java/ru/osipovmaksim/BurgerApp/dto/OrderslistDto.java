package ru.osipovmaksim.BurgerApp.dto;

import java.io.Serializable;

/**
 * DTO for {@link ru.osipovmaksim.BurgerApp.entity.Orderslist}
 */
public record OrderslistDto(OrderDto order, BurgerDto burger, Integer count) implements Serializable {
}