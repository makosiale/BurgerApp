package ru.osipovmaksim.BurgerApp.entity.view;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSummaryEntity {
    int order_id;
    String username;
    String employee;
    String name;
    int quantity;
    double price;
}
