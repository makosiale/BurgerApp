package ru.osipovmaksim.BurgerApp.dto.request;

import java.util.List;

public record RequestOrderDto(String username, List<BurgerDetails> positions) {
    public record BurgerDetails(int burgerId, int quantity) {
    }
}