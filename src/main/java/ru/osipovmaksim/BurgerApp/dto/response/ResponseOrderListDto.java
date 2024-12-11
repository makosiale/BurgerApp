package ru.osipovmaksim.BurgerApp.dto.response;

import java.util.List;

public record ResponseOrderListDto(int id, List<BurgerDetails> burgers, int cost) {
    public record BurgerDetails(String name, int count) {
    }
}
