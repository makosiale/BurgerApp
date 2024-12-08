package ru.osipovmaksim.BurgerApp.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record ResponseOrderSummaryDto(int orderId, String username, String employee, List<BurgerRecordDto> burgers, BigDecimal totalPrice) {
    public record BurgerRecordDto(String name, int quantity) {}
}