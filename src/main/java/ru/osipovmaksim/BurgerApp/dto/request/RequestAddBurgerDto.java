package ru.osipovmaksim.BurgerApp.dto.request;

import java.util.List;

public record RequestAddBurgerDto(String name, double cost, List<ReceptBurgerDto> ingredients) {
    public record ReceptBurgerDto(int ingredientId, int quantity) {
    }
}
