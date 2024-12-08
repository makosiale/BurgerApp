package ru.osipovmaksim.BurgerApp.dto.response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public record ResponseUserOrderListDto(int id, List<BurgerPosition> positions) {

    public ResponseUserOrderListDto(int id, String positionsJson) {
        this(id, parsePositionsJson(positionsJson));
    }

    // Метод для преобразования JSON в список объектов
    private static List<BurgerPosition> parsePositionsJson(String positionsJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(positionsJson, new TypeReference<List<BurgerPosition>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return List.of(); // Если ошибка, возвращаем пустой список
        }
    }

    // Вложенный класс для позиции бургера
    public record BurgerPosition(String burgerName, int quantity) {}
}
