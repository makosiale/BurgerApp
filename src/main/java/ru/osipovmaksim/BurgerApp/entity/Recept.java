package ru.osipovmaksim.BurgerApp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Getter
@Setter
@Entity
@DynamicInsert
@NoArgsConstructor
@Builder
@Table(name = "recepts")
@IdClass(ReceptId.class)
@AllArgsConstructor
public class Recept {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "burger_id", nullable = false)
    private Burger burger;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
