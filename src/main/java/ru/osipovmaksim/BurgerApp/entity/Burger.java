package ru.osipovmaksim.BurgerApp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.math.BigDecimal;

@Getter
@Setter
@DynamicInsert
@Entity
@Builder
@NoArgsConstructor
@Table(name = "burgers")
@AllArgsConstructor
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