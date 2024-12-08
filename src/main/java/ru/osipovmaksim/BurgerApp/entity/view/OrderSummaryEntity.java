package ru.osipovmaksim.BurgerApp.entity.view;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

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
