package ru.osipovmaksim.BurgerApp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.math.BigDecimal;

@Getter
@Setter
@DynamicInsert
@Entity
@Table(name = "burgers")
public class Burger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "burger_id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "cost", precision = 10, scale = 2)
    private BigDecimal cost;

}